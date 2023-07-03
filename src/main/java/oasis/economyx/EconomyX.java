package oasis.economyx;

import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.listeners.actor.ActorAddressChangedListener;
import oasis.economyx.listeners.actor.ActorCreationListener;
import oasis.economyx.listeners.actor.ActorNameChangedListener;
import oasis.economyx.listeners.asset.AssetDephysicalizedListener;
import oasis.economyx.listeners.asset.AssetPhysicalizedListener;
import oasis.economyx.listeners.banking.BankAccountClosedListener;
import oasis.economyx.listeners.banking.BankAccountOpenedListener;
import oasis.economyx.listeners.banking.BankDepositListener;
import oasis.economyx.listeners.banking.BankWithdrawalListener;
import oasis.economyx.listeners.banknote.BanknoteIssuedListener;
import oasis.economyx.listeners.card.CardActivatedListener;
import oasis.economyx.listeners.card.CardIssuedListener;
import oasis.economyx.listeners.card.CardRevokedListener;
import oasis.economyx.listeners.card.CardUsedListener;
import oasis.economyx.listeners.contract.ContractCreatedListener;
import oasis.economyx.listeners.contract.ContractExpiredListener;
import oasis.economyx.listeners.contract.ContractForgivenListener;
import oasis.economyx.listeners.contract.OptionExercisedListener;
import oasis.economyx.listeners.dividend.DividendListener;
import oasis.economyx.listeners.guarantee.GuaranteeIssuedListener;
import oasis.economyx.listeners.guarantee.GuaranteeRevokedListener;
import oasis.economyx.listeners.organization.AllianceMemberChangedListener;
import oasis.economyx.listeners.organization.CartelMemberChangedListener;
import oasis.economyx.listeners.organization.PartyMemberChangedListener;
import oasis.economyx.listeners.payment.PaymentListener;
import oasis.economyx.listeners.personal.EmploymentListener;
import oasis.economyx.listeners.personal.RepresentableEventListener;
import oasis.economyx.listeners.player.PlayerDeathHandler;
import oasis.economyx.listeners.player.PlayerJoinHandler;
import oasis.economyx.listeners.property.PropertyClaimHandler;
import oasis.economyx.listeners.property.PropertyProtectionHandler;
import oasis.economyx.listeners.stock.StockIssuedListener;
import oasis.economyx.listeners.stock.StockRetiredListener;
import oasis.economyx.listeners.stock.StockSplitListener;
import oasis.economyx.listeners.terminal.CardTerminalCreatedListener;
import oasis.economyx.listeners.terminal.CardTerminalDestroyedListener;
import oasis.economyx.listeners.trading.listing.AssetDelistedListener;
import oasis.economyx.listeners.trading.listing.AssetListedListener;
import oasis.economyx.listeners.trading.order.OrderCancelledListener;
import oasis.economyx.listeners.trading.order.OrderPlacedListener;
import oasis.economyx.listeners.vaulting.VaultCreatedListener;
import oasis.economyx.listeners.vaulting.VaultDestroyedListener;
import oasis.economyx.listeners.vaulting.VaultOpenedListener;
import oasis.economyx.listeners.voting.VoteCastListener;
import oasis.economyx.listeners.voting.VoteProposedListener;
import oasis.economyx.listeners.warfare.HostilityStateChangedListener;
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
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Main class of EconomyX.
 *
 * <p>
 * EconomyX uses events to receive input from clients.
 * </p>
 */
public final class EconomyX extends JavaPlugin {
    private EconomyState state;

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

        // Asset
        registerListener(new AssetDephysicalizedListener(this, state));
        registerListener(new AssetPhysicalizedListener(this, state));

        // Banking
        registerListener(new BankDepositListener(this, state));
        registerListener(new BankWithdrawalListener(this, state));
        registerListener(new BankAccountOpenedListener(this, state));
        registerListener(new BankAccountClosedListener(this, state));

        // Banknote
        registerListener(new BanknoteIssuedListener(this, state));

        // Card
        registerListener(new CardActivatedListener(this, state));
        registerListener(new CardIssuedListener(this, state));
        registerListener(new CardUsedListener(this, state));
        registerListener(new CardRevokedListener(this, state));

        // Contract
        registerListener(new ContractCreatedListener(this, state));
        registerListener(new ContractExpiredListener(this, state));
        registerListener(new ContractForgivenListener(this, state));
        registerListener(new OptionExercisedListener(this, state));

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
        registerListener(new PlayerDeathHandler(this, state));

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
