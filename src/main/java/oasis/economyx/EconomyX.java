package oasis.economyx;

import oasis.economyx.classes.actor.company.common.Manufacturer;
import oasis.economyx.classes.actor.person.NaturalPerson;
import oasis.economyx.classes.voting.common.DummyAgenda;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.listeners.actor.ActorAddressChangedListener;
import oasis.economyx.listeners.actor.ActorNameChangedListener;
import oasis.economyx.listeners.banking.BankDepositListener;
import oasis.economyx.listeners.banking.BankWithdrawalListener;
import oasis.economyx.listeners.actor.ActorCreationListener;
import oasis.economyx.listeners.card.CardActivatedListener;
import oasis.economyx.listeners.card.CardIssuedListener;
import oasis.economyx.listeners.card.CardUsedListener;
import oasis.economyx.listeners.contract.ContractCreatedListener;
import oasis.economyx.listeners.contract.ContractExpiredListener;
import oasis.economyx.listeners.contract.ContractForgivenListener;
import oasis.economyx.listeners.dividend.DividendListener;
import oasis.economyx.listeners.guarantee.GuaranteeIssuedListener;
import oasis.economyx.listeners.guarantee.GuaranteeRevokedListener;
import oasis.economyx.listeners.organization.AllianceMemberChangedListener;
import oasis.economyx.listeners.organization.CartelMemberChangedListener;
import oasis.economyx.listeners.organization.PartyMemberChangedListener;
import oasis.economyx.listeners.payment.PaymentListener;
import oasis.economyx.listeners.personal.EmploymentListener;
import oasis.economyx.listeners.personal.RepresentableEventListener;
import oasis.economyx.listeners.player.PlayerJoinHandler;
import oasis.economyx.listeners.stock.StockIssuedListener;
import oasis.economyx.listeners.stock.StockRetiredListener;
import oasis.economyx.listeners.stock.StockSplitListener;
import oasis.economyx.listeners.terminal.CardTerminalCreatedListener;
import oasis.economyx.listeners.terminal.CardTerminalDestroyedListener;
import oasis.economyx.listeners.trading.AssetDelistedListener;
import oasis.economyx.listeners.trading.AssetListedListener;
import oasis.economyx.listeners.trading.OrderCancelledListener;
import oasis.economyx.listeners.trading.OrderPlacedListener;
import oasis.economyx.listeners.vaulting.VaultCreatedListener;
import oasis.economyx.listeners.vaulting.VaultDestroyedListener;
import oasis.economyx.listeners.vaulting.VaultOpenedListener;
import oasis.economyx.listeners.voting.VoteCastListener;
import oasis.economyx.listeners.voting.VoteProposedListener;
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

        registerListeners();
        registerTasks();



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

    private void registerTasks() {
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
    }

    private void registerListeners() {
        //
        // Listeners
        //

        // Actor
        registerListener(new ActorAddressChangedListener(this));
        registerListener(new ActorCreationListener(this));
        registerListener(new ActorNameChangedListener(this));

        // Banking
        registerListener(new BankDepositListener(this));
        registerListener(new BankWithdrawalListener(this));

        // Card
        registerListener(new CardActivatedListener(this));
        registerListener(new CardIssuedListener(this));
        registerListener(new CardUsedListener(this));

        // Contract
        registerListener(new ContractCreatedListener(this));
        registerListener(new ContractExpiredListener(this));
        registerListener(new ContractForgivenListener(this));

        // Dividend
        registerListener(new DividendListener(this));

        // Guarantee
        registerListener(new GuaranteeIssuedListener(this));
        registerListener(new GuaranteeRevokedListener(this));

        // Organization
        registerListener(new AllianceMemberChangedListener(this));
        registerListener(new CartelMemberChangedListener(this));
        registerListener(new PartyMemberChangedListener(this));

        // Payments
        registerListener(new PaymentListener(this));

        // Personal
        registerListener(new EmploymentListener(this));
        registerListener(new RepresentableEventListener(this));

        // Player
        registerListener(new PlayerJoinHandler(this));

        // Stock
        registerListener(new StockIssuedListener(this));
        registerListener(new StockSplitListener(this));
        registerListener(new StockRetiredListener(this));

        // Terminal
        registerListener(new CardTerminalCreatedListener(this));
        registerListener(new CardTerminalDestroyedListener(this));

        // Trading
        registerListener(new AssetListedListener(this));
        registerListener(new AssetDelistedListener(this));

        registerListener(new OrderPlacedListener(this));
        registerListener(new OrderCancelledListener(this));

        // Vaulting
        registerListener(new VaultCreatedListener(this));
        registerListener(new VaultOpenedListener(this));
        registerListener(new VaultDestroyedListener(this));

        // Voting
        registerListener(new VoteCastListener(this));
        registerListener(new VoteProposedListener(this));
    }

    private void registerTask(EconomyTask task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, task.getDelay(), task.getInterval());
    }

    private void registerListener(EconomyListener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }
}
