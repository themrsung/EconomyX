package oasis.economyx;

import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.listeners.*;
import oasis.economyx.listeners.actor.*;
import oasis.economyx.listeners.banking.*;
import oasis.economyx.listeners.card.*;
import oasis.economyx.listeners.contract.*;
import oasis.economyx.listeners.dividend.*;
import oasis.economyx.listeners.guarantee.*;
import oasis.economyx.listeners.organization.*;
import oasis.economyx.listeners.payment.*;
import oasis.economyx.listeners.personal.*;
import oasis.economyx.listeners.player.*;
import oasis.economyx.listeners.property.*;
import oasis.economyx.listeners.stock.*;
import oasis.economyx.listeners.terminal.*;
import oasis.economyx.listeners.trading.*;
import oasis.economyx.listeners.vaulting.*;
import oasis.economyx.listeners.voting.*;
import oasis.economyx.listeners.warfare.*;
import oasis.economyx.state.*;
import oasis.economyx.tasks.*;
import oasis.economyx.tasks.expiry.*;
import oasis.economyx.tasks.gaming.*;
import oasis.economyx.tasks.payment.*;
import oasis.economyx.tasks.server.*;
import oasis.economyx.tasks.trading.*;
import oasis.economyx.tasks.voting.*;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Main class of EconomyX.
 *
 * <p>
 * EconomyX uses events to receive input from clients.
 * Direct state access is impossible.
 * States are given out as deep copies to server implementations.
 * They are censored for clients. (see {@link EconomyX#getCensoredState(Person)}}
 * </p>
 */
public class EconomyX extends JavaPlugin {
    private EconomyState state;

    /**
     * Gets a deep copy of the state.
     * Do NOT give this to clients.
     *
     * @return Deep copy
     */
    @NonNull
    EconomyState getCopiedState() {
        return state.copy();
    }

    /**
     * Gets a censored deep copy of the state.
     *
     * @param viewer Viewer to censor the state as
     * @return Censored deep copy
     */
    @NonNull
    EconomyState getCensoredState(@NonNull Person viewer) {
        return state.censor(viewer);
    }

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Loading EconomyX.");

        this.state = EconomyXState.load(this);

        registerListeners();
        registerTasks();

        Bukkit.getLogger().info("EconomyX loaded.");
    }

    @Override
    public void onDisable() {
        state.save();

        Bukkit.getLogger().info("EconomyX unloaded.");
    }

    private void registerTasks() {
        //
        // Tasks
        //

        // Expiry
        registerTask(new CardExpiryTask(this, state));
        registerTask(new ContractExpiryTask(this, state));

        // Gaming
        registerTask(new CasinoProgressTask(this, state));

        // Payments
        registerTask(new RegularPaymentTask(this, state));
        registerTask(new CreditCardSettlementTask(this, state));

        // Server
        registerTask(new AutoSaveTask(this, state));

        // Trading
        registerTask(new AuctionTickTask(this, state));
        registerTask(new MarketTickTask(this, state));

        // Voting
        registerTask(new VoteProcessTask(this, state));
    }

    private void registerListeners() {
        //
        // Listeners
        //

        // Actor
        registerListener(new ActorAddressChangedListener(this, state));
        registerListener(new ActorCreationListener(this, state));
        registerListener(new ActorNameChangedListener(this, state));

        // Banking
        registerListener(new BankDepositListener(this, state));
        registerListener(new BankWithdrawalListener(this, state));

        // Card
        registerListener(new CardActivatedListener(this, state));
        registerListener(new CardIssuedListener(this, state));
        registerListener(new CardUsedListener(this, state));
        registerListener(new CardRevokedListener(this, state));

        // Contract
        registerListener(new ContractCreatedListener(this, state));
        registerListener(new ContractExpiredListener(this, state));
        registerListener(new ContractForgivenListener(this, state));

        // Dividend
        registerListener(new DividendListener(this, state));

        // Guarantee
        registerListener(new GuaranteeIssuedListener(this, state));
        registerListener(new GuaranteeRevokedListener(this, state));

        // Organization
        registerListener(new AllianceMemberChangedListener(this, state));
        registerListener(new CartelMemberChangedListener(this, state));
        registerListener(new PartyMemberChangedListener(this, state));

        // Payments
        registerListener(new PaymentListener(this, state));

        // Personal
        registerListener(new EmploymentListener(this, state));
        registerListener(new RepresentableEventListener(this, state));

        // Player
        registerListener(new PlayerJoinHandler(this, state));

        // Property
        registerListener(new PropertyClaimHandler(this, state));
        registerListener(new PropertyProtectionHandler(this, state));

        // Stock
        registerListener(new StockIssuedListener(this, state));
        registerListener(new StockSplitListener(this, state));
        registerListener(new StockRetiredListener(this, state));

        // Terminal
        registerListener(new CardTerminalCreatedListener(this, state));
        registerListener(new CardTerminalDestroyedListener(this, state));

        // Trading
        registerListener(new AssetListedListener(this, state));
        registerListener(new AssetDelistedListener(this, state));

        registerListener(new OrderPlacedListener(this, state));
        registerListener(new OrderCancelledListener(this, state));

        // Vaulting
        registerListener(new VaultCreatedListener(this, state));
        registerListener(new VaultOpenedListener(this, state));
        registerListener(new VaultDestroyedListener(this, state));

        // Voting
        registerListener(new VoteCastListener(this, state));
        registerListener(new VoteProposedListener(this, state));

        // Warfare
        registerListener(new HostilityStateChangedListener(this, state));
    }

    private void registerTask(EconomyTask task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, task.getDelay(), task.getInterval());
    }

    private void registerListener(EconomyListener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }
}
