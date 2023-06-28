package oasis.economyx;

import com.google.inject.Inject;
import net.kyori.adventure.identity.Identity;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.LinearComponents;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.Style;
import net.kyori.adventure.text.format.TextDecoration;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.asset.cash.CashMeta;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.asset.contract.note.Note;
import oasis.economyx.asset.contract.note.NoteStack;
import oasis.economyx.asset.stock.Stock;
import oasis.economyx.asset.stock.StockMeta;
import oasis.economyx.asset.stock.StockStack;
import oasis.economyx.classes.Company;
import oasis.economyx.classes.NaturalPerson;
import oasis.economyx.portfolio.AssetPortfolio;
import oasis.economyx.state.EconomyState;
import oasis.economyx.state.EconomyXState;
import org.apache.logging.log4j.Logger;
import org.joda.time.DateTime;
import org.spongepowered.api.Server;
import org.spongepowered.api.command.Command;
import org.spongepowered.api.command.CommandResult;
import org.spongepowered.api.command.parameter.Parameter;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.event.lifecycle.ConstructPluginEvent;
import org.spongepowered.api.event.lifecycle.RegisterCommandEvent;
import org.spongepowered.api.event.lifecycle.StartingEngineEvent;
import org.spongepowered.api.event.lifecycle.StoppingEngineEvent;
import org.spongepowered.plugin.PluginContainer;
import org.spongepowered.plugin.builtin.jvm.Plugin;

import java.util.ArrayList;
import java.util.UUID;

/**
 * The main class of your Sponge plugin.
 *
 * <p>All methods are optional -- some common event registrations are included as a jumping-off point.</p>
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

    @Listener
    public void onConstructPlugin(final ConstructPluginEvent event) {
        this.logger.info("Loading EconomyX.");

        this.state = new EconomyXState(this);
    }

    @Listener
    public void onServerStarting(final StartingEngineEvent<Server> event) {
        // Assuming game state has been saved and can be safely wiped.
        this.state = EconomyXState.load(this);

        Company comp = new Company(UUID.randomUUID(), "Oasis Corporation", UUID.randomUUID(), 100L);
        this.state.addActor(comp);

        NaturalPerson np = new NaturalPerson(UUID.randomUUID(), "Parzival");
        this.state.addActor(np);

        UUID currency = UUID.randomUUID();

        np.getAssets().add(new CashStack(new Cash(currency), 100L));
        comp.getAssets().add(new StockStack(new Stock(comp.getStockId()), 100L));

        Note note = new Note(
                UUID.randomUUID(),
                comp,
                new CashStack(new Cash(currency), 1000L),
                new DateTime().plusDays(10)
        );

        NoteStack ns = new NoteStack(note, 100);

        np.getAssets().add(ns);

        this.state.save();

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
        // When possible, all commands should be registered within a command register event
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
}
