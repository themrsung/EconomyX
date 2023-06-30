package oasis.economyx;

import oasis.economyx.classes.actor.company.common.Manufacturer;
import oasis.economyx.classes.actor.person.NaturalPerson;
import oasis.economyx.classes.voting.common.DummyAgenda;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import oasis.economyx.listener.EconomyListener;
import oasis.economyx.listener.dividend.DividendListener;
import oasis.economyx.listener.payment.PaymentListener;
import oasis.economyx.listener.player.PlayerJoinHandler;
import oasis.economyx.listener.stock.StockSplitListener;
import oasis.economyx.listener.vault.VaultOpenedListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.state.EconomyXState;
import oasis.economyx.tasks.EconomyTask;
import oasis.economyx.tasks.expiry.CardExpiryTask;
import oasis.economyx.tasks.expiry.ContractExpiryTask;
import oasis.economyx.tasks.gaming.CasinoProgressTask;
import oasis.economyx.tasks.payment.CreditCardSettlementTask;
import oasis.economyx.tasks.payment.RegularPaymentTask;
import oasis.economyx.tasks.server.AutoSaveTask;
import oasis.economyx.tasks.trading.AuctionTickTask;
import oasis.economyx.tasks.trading.MarketTickTask;
import oasis.economyx.tasks.voting.VoteProcessTask;
import oasis.economyx.types.asset.cash.Cash;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * Main class of EconomyX
 */
public final class EconomyX extends JavaPlugin {
    private EconomyState state;

    public EconomyState getState() {
        return state;
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Loading EconomyX.");

        this.state = EconomyXState.load(this);

        //
        // Listeners
        //

        // Dividends
        registerListener(new DividendListener(this));

        // Stock
        registerListener(new StockSplitListener(this));

        // Payments
        registerListener(new PaymentListener(this));

        // Player
        registerListener(new PlayerJoinHandler(this));

        // Vault
        registerListener(new VaultOpenedListener(this));

        //
        // Tasks
        //

        // Expiry
        registerTask(new CardExpiryTask(this));
        registerTask(new ContractExpiryTask(this));

        // Gaming
        registerTask(new CasinoProgressTask(this));

        // Payments
        registerTask(new RegularPaymentTask(this));
        registerTask(new CreditCardSettlementTask(this));

        // Server
        registerTask(new AutoSaveTask(this));

        // Trading
        registerTask(new AuctionTickTask(this));
        registerTask(new MarketTickTask(this));

        // Voting
        registerTask(new VoteProcessTask(this));

        // DEBUG

        Cash currency = new Cash(UUID.randomUUID());
        Manufacturer m = new Manufacturer(UUID.randomUUID(), "Mfc", UUID.randomUUID(), 100, currency);

        Person p = new NaturalPerson(UUID.randomUUID(), "Parzival");

        getState().addActor(m);
        getState().addActor(p);

        List<Voter> voters = new ArrayList<>();
        voters.add(Voter.get(m, 1));
        Voter mv = Voter.get(p, 100);
        voters.add(mv);

        Vote v = Vote.getBooleanVote(
                UUID.randomUUID(),
                "test",
                voters,
                new DummyAgenda("23232"),
                new DateTime().plusDays(1),
                0.1f,
                10);


        m.openVote(v);

        v.vote(mv, v.getCandidates().get(0), 50);


        // DEBUG


        Bukkit.getLogger().info("EconomyX loaded.");
    }

    @Override
    public void onDisable() {
        getState().save();

        Bukkit.getLogger().info("EconomyX unloaded.");
    }

    private void registerTask(EconomyTask task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, task.getDelay(), task.getInterval());
    }

    private void registerListener(EconomyListener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }
}
