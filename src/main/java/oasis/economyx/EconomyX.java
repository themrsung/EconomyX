package oasis.economyx;

import com.google.inject.Inject;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.LinearComponents;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.actor.types.trading.AuctionHost;
import oasis.economyx.interfaces.actor.types.trading.MarketHost;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.listener.PaymentListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.state.EconomyXState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.Contract;
import org.apache.logging.log4j.Logger;
import org.spongepowered.api.Server;
import org.spongepowered.api.Sponge;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.api.scheduler.Task;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.util.concurrent.TimeUnit;

/**
 * The main class of your Sponge plugin.
 *
 * <p>All methods are optional -- some manufacturing events registrations are included as a jumping-off point.</p>
 */
@Plugin("economyx")
public class EconomyX {
    private final PluginContainer container;
    private final Logger logger;
    public Logger getLogger() {
        return logger;
    }

    @Inject
    EconomyX(final PluginContainer container, final Logger logger) {
        this.container = container;
        this.logger = logger;
    }

    private EconomyState state;

    public EconomyState getState() {
        return state;
    }

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        this.logger.info("Loading EconomyX.");

        Sponge.eventManager().registerListeners(container, new PaymentListener(this));

        registerTasks();

        this.state = new EconomyXState(this);
    }

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        // Assuming game state has been saved and can be safely wiped.
        this.state = EconomyXState.load(this);
//
//        Company comp = new Company(UUID.randomUUID(), "Oasis Corporation", UUID.randomUUID(), 100L);
//        this.state.addActor(comp);
//
//        NaturalPerson np = new NaturalPerson(UUID.randomUUID(), "Parzival");
//        this.state.addActor(np);
//
//        UUID currency = UUID.randomUUID();
//
//        np.getAssets().add(new CashStack(new Cash(currency), 100L));
//        comp.getAssets().add(new StockStack(new Stock(comp.getStockId()), 100L));
//
//        Note note = new Note(
//                UUID.randomUUID(),
//                comp,
//                new CashStack(new Cash(currency), 1000L),
//                new DateTime().plusDays(10)
//        );
//
//        NoteStack ns = new NoteStack(note, 100);
//
//        np.getAssets().add(ns);
//
//        this.state.save();


        this.logger.info("Plugin loaded.");
    }

    @Listener
    public void onServerStopping(final StoppingEngineEvent<Server> event) {
        this.state.save();
        this.logger.info("Plugin unloaded.");
    }

    @Listener
    public void onRegisterCommands(final RegisterCommandEvent<Command.Parameterized> event) {
        // Register a simple command
        // When possible, all commands should be registered within a command register events
        final Parameter.Value<String> nameParam = Parameter.string().key("name").build();
        event.register(this.container, Command.builder()
            .addParameter(nameParam)
            .permission("EconomyX.command.greet")
            .executor(ctx -> {
                final String name = ctx.requireOne(nameParam);
                ctx.sendMessage(Identity.nil(), LinearComponents.linear(
                    NamedTextColor.AQUA,
                    Component.text("Hello "),
                    Component.text(name, Style.style(TextDecoration.BOLD)),
                    Component.text("!")
                ));

                return CommandResult.success();
            })
            .build(), "greet", "wave");
    }

    private void registerTasks() {
        Task.Builder builder = Task.builder();

        // Payments
        Task paymentTask = builder.execute(() -> {
            for (Banker b : getState().getBankers()) {
                b.payInterest();
            }

            for (Employer e : getState().getEmployers()) {
                e.paySalaries();
            }

            for (Representable r : getState().getRepresentables()) {
                r.payRepresentative();
            }
        }).interval(1, TimeUnit.HOURS).delay(1, TimeUnit.SECONDS).build();

        Sponge.asyncScheduler().submit(paymentTask);

        // Contract expiry
        Task contractExpiryTask = builder.execute(() -> {
            for (Actor a : getState().getActors()) {
                for (AssetStack as : a.getAssets().get()) {
                    if (as.getAsset() instanceof Contract c) {

                        // Contract expiry has to be called once for every contract instance
                        for (int i = 0; i < as.getQuantity(); i++) {
                            c.onExpired(a);
                        }

                        // Delete contract
                        a.getAssets().remove(as);
                    }
                }
            }
        }).interval(1, TimeUnit.HOURS).delay(1, TimeUnit.SECONDS).build();

        Sponge.asyncScheduler().submit(contractExpiryTask);

        // Card expiry
        Task cardExpiryTask = builder.execute(() -> {
            for (Card c : getState().getCards()) {
                c.onExpired();
            }
        }).interval(1, TimeUnit.HOURS).delay(1, TimeUnit.SECONDS).build();

        Sponge.asyncScheduler().submit(cardExpiryTask);

        // Market ticks
        Task marketTickTask = builder.execute(() -> {
            for (MarketHost h : getState().getMarketHosts()) {
                for (Marketplace m : h.getMarkets()) {
                    m.processOrders(h);
                }
            }
        }).interval(75, TimeUnit.MILLISECONDS).delay(1, TimeUnit.SECONDS).build();

        Sponge.asyncScheduler().submit(marketTickTask);

        // Auction ticks
        Task auctionTickTask = builder.execute(() -> {
            for (AuctionHost h : getState().getAuctionHosts()) {
                for (Auctioneer a : h.getAuctions()) {
                    a.processBids(h);

                    if (a.hasExpired()) a.onDeadlineReached(h);
                }
            }
        }).interval(1, TimeUnit.SECONDS).delay(1, TimeUnit.SECONDS).build();

        Sponge.asyncScheduler().submit(auctionTickTask);
    }
}
