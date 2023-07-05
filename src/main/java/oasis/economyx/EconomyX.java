package oasis.economyx;

import oasis.economyx.classes.actor.company.special.HoldingsCompany;
import oasis.economyx.classes.actor.institution.monetary.CentralBank;
import oasis.economyx.classes.actor.institution.monetary.Mint;
import oasis.economyx.classes.actor.institution.warfare.Military;
import oasis.economyx.classes.actor.institution.warfare.ResearchCenter;
import oasis.economyx.classes.actor.organization.international.Alliance;
import oasis.economyx.classes.actor.person.NaturalPerson;
import oasis.economyx.classes.actor.sovereignty.federal.Empire;
import oasis.economyx.classes.actor.sovereignty.singular.Principality;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.commands.address.AddressCommand;
import oasis.economyx.commands.address.SetAddressCommand;
import oasis.economyx.commands.asset.DephysicalizeAssetCommand;
import oasis.economyx.commands.asset.PhysicalizeAssetCommand;
import oasis.economyx.commands.asset.SendAssetCommand;
import oasis.economyx.commands.balance.BalanceCommand;
import oasis.economyx.commands.create.CreateCommand;
import oasis.economyx.commands.info.InformationCommand;
import oasis.economyx.commands.join.JoinCommand;
import oasis.economyx.commands.management.*;
import oasis.economyx.commands.message.MessageCommand;
import oasis.economyx.commands.message.ReplyCommand;
import oasis.economyx.commands.offer.OfferCommand;
import oasis.economyx.commands.pay.PayCommand;
import oasis.economyx.commands.property.PropertyAbandonCommand;
import oasis.economyx.commands.property.PropertyClaimCommand;
import oasis.economyx.commands.property.PropertySetProtectorCommand;
import oasis.economyx.commands.retire.RetireCommand;
import oasis.economyx.commands.sudo.SudoCommand;
import oasis.economyx.commands.trading.order.OrderCommand;
import oasis.economyx.commands.voting.VoteCommand;
import oasis.economyx.commands.warfare.HostilityCommand;
import oasis.economyx.interfaces.actor.person.Person;
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
import oasis.economyx.listeners.currency.CurrencyIssuedListener;
import oasis.economyx.listeners.dividend.DividendListener;
import oasis.economyx.listeners.guarantee.GuaranteeIssuedListener;
import oasis.economyx.listeners.guarantee.GuaranteeRevokedListener;
import oasis.economyx.listeners.management.PropertyProtectorManagedListener;
import oasis.economyx.listeners.message.MessageSentListener;
import oasis.economyx.listeners.offer.OfferEventListener;
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
import oasis.economyx.listeners.sovereign.SovereignMemberChangedListener;
import oasis.economyx.listeners.stock.StockIssuedListener;
import oasis.economyx.listeners.stock.StockRetiredListener;
import oasis.economyx.listeners.stock.StockSplitListener;
import oasis.economyx.listeners.tax.TaxListener;
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
import oasis.economyx.tasks.message.MessageHistoryCleanerTask;
import oasis.economyx.tasks.message.MessengerTask;
import oasis.economyx.tasks.payment.CreditCardSettlementTask;
import oasis.economyx.tasks.payment.RegularPaymentTask;
import oasis.economyx.tasks.server.AutoSaveTask;
import oasis.economyx.tasks.trading.AuctionTickTask;
import oasis.economyx.tasks.trading.MarketTickTask;
import oasis.economyx.tasks.voting.VoteProcessTask;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Objects;
import java.util.UUID;

/**
 * Main class of EconomyX.
 *
 * <p>
 * EconomyX uses events to receive input from clients.
 * </p>
 */
public final class EconomyX extends JavaPlugin {
    private EconomyState state;

    // Server settings
    public static Cash SERVER_CURRENCY = new Cash(UUID.fromString("85045e1c-2a32-41af-8728-950b9fad1368"), "CR");

    @Override
    public void onEnable() {
        Bukkit.getLogger().info("Loading EconomyX.");

        this.state = EconomyXState.load(this);

        if (state.getActors().size() == 0) initializeDefaultState();

        registerListeners();
        registerTasks();
        registerCommands();

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

        // Message
        registerTask(new MessengerTask(this, state));
        registerTask(new MessageHistoryCleanerTask(this, state));

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

        // Currency
        registerListener(new CurrencyIssuedListener(this, state));

        // Dividend
        registerListener(new DividendListener(this, state));

        // Guarantee
        registerListener(new GuaranteeIssuedListener(this, state));
        registerListener(new GuaranteeRevokedListener(this, state));

        // Management
        registerListener(new PropertyProtectorManagedListener(this, state));

        // Message
        registerListener(new MessageSentListener(this, state));

        // Offer
        registerListener(new OfferEventListener(this, state));

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

        // Sovereign
        registerListener(new SovereignMemberChangedListener(this, state));

        // Stock
        registerListener(new StockIssuedListener(this, state));
        registerListener(new StockSplitListener(this, state));
        registerListener(new StockRetiredListener(this, state));

        // Tax
        registerListener(new TaxListener(this, state));

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

    private void registerCommands() {
        registerCommand("balance", new BalanceCommand(this, state));
        registerCommand("sudo", new SudoCommand(this, state));

        registerCommand("create", new CreateCommand(this, state));
        registerCommand("pay", new PayCommand(this, state));

        registerCommand("message", new MessageCommand(this, state));
        registerCommand("reply", new ReplyCommand(this, state));

        registerCommand("setaddress", new SetAddressCommand(this, state));
        registerCommand("address", new AddressCommand(this, state));

        registerCommand("physicalize", new PhysicalizeAssetCommand(this, state));
        registerCommand("dephysicalize", new DephysicalizeAssetCommand(this, state));
        registerCommand("sendasset", new SendAssetCommand(this, state));

        registerCommand("information", new InformationCommand(this, state));

        registerCommand("offer", new OfferCommand(this, state));
        registerCommand("retire", new RetireCommand(this, state));
        registerCommand("join", new JoinCommand(this, state));

        registerCommand("claim", new PropertyClaimCommand(this, state));
        registerCommand("abandon", new PropertyAbandonCommand(this, state));
        registerCommand("setprotector", new PropertySetProtectorCommand(this, state));

        registerCommand("vote", new VoteCommand(this, state));

        registerCommand("propertyprotection", new PropertyProtectionCommand(this, state));
        registerCommand("manageinstitution", new ManageInstitutionCommand(this, state));
        registerCommand("issuecurrency", new IssueCurrencyCommand(this, state));
        registerCommand("changetaxrate", new ChangeTaxRateCommand(this, state));
        registerCommand("hostility", new HostilityCommand(this, state));
        registerCommand("assetlisting", new AssetListingCommand(this, state));

        registerCommand("order", new OrderCommand(this, state));
    }

    private void registerTask(EconomyTask task) {
        Bukkit.getScheduler().scheduleSyncRepeatingTask(this, task, task.getDelay(), task.getInterval());
    }

    private void registerListener(EconomyListener event) {
        Bukkit.getPluginManager().registerEvents(event, this);
    }

    private void registerCommand(@NonNull String name, EconomyCommand command) {
        Objects.requireNonNull(getCommand(name)).setExecutor(command);
        Objects.requireNonNull(getCommand(name)).setTabCompleter(command);
    }

    /**
     * Called on first startup.
     * Customize this before running the plugin.
     */
    private void initializeDefaultState() {
        // Founder
        Person halliday = new NaturalPerson(
                UUID.fromString("43c8a60f-e288-4ed3-a4af-fb220faad455"),
                "themrsung"
        );

        halliday.getAssets().add(new CashStack(SERVER_CURRENCY, 10000L));
        state.addActor(halliday);

        // Oasis
        Stock oasisStock = new Stock(UUID.randomUUID(), "OAS");
        HoldingsCompany oasis = new HoldingsCompany(
                UUID.randomUUID(),
                "OasisCorporation",
                oasisStock.getUniqueId(),
                10000L,
                SERVER_CURRENCY
        );

        halliday.getAssets().add(new StockStack(oasisStock, 10000L));
        oasis.getAssets().add(new CashStack(SERVER_CURRENCY, 1000000000000000000L));
        oasis.setRepresentative(halliday);
        oasis.setRepresentativePay(new CashStack(SERVER_CURRENCY, 1000000L));

        state.addActor(oasis);

        // JB_PRINCIPALITY
        Principality JB_PRINCIPALITY = new Principality(
                UUID.randomUUID(),
                "자본주의공국",
                SERVER_CURRENCY,
                halliday
        );

        JB_PRINCIPALITY.getAssets().add(new CashStack(SERVER_CURRENCY, 10000000L));
        JB_PRINCIPALITY.addCorporation(oasis);
        JB_PRINCIPALITY.setRepresentative(halliday);

        state.addActor(JB_PRINCIPALITY);

        // JB Empire
        Empire JBE = new Empire(
                UUID.randomUUID(),
                "자본주의제국",
                SERVER_CURRENCY,
                JB_PRINCIPALITY
        );

        JBE.getAssets().add(new CashStack(SERVER_CURRENCY, 10000000L));
        JBE.setRepresentative(halliday);

        state.addActor(JBE);

        // JB Bank
        CentralBank JB_BANK = new CentralBank(
                JBE,
                UUID.randomUUID(),
                "JB중앙은행",
                SERVER_CURRENCY
        );

        JB_BANK.setRepresentative(halliday);
        state.addActor(JB_BANK);
        JBE.addInstitution(JB_BANK);

        // JB Mint
        Mint JB_MINT = new Mint(
                JBE,
                UUID.randomUUID(),
                "JB조폐국",
                SERVER_CURRENCY
        );
        JB_MINT.setRepresentative(halliday);
        state.addActor(JB_MINT);
        JBE.addInstitution(JB_MINT);

        // JB Research Center
        ResearchCenter JB_RESEARCH = new ResearchCenter(
                JBE,
                UUID.randomUUID(),
                "JB연구소",
                SERVER_CURRENCY
        );
        JB_RESEARCH.setRepresentative(halliday);
        state.addActor(JB_RESEARCH);
        JBE.addInstitution(JB_RESEARCH);

        // JB Peacekeepers
        Military JB_PEACEKEEPERS = new Military(
                JBE,
                UUID.randomUUID(),
                "JB평화유지군",
                SERVER_CURRENCY
        );

        JB_PEACEKEEPERS.setRepresentative(halliday);
        state.addActor(JB_PEACEKEEPERS);
        JBE.addInstitution(JB_PEACEKEEPERS);

        // MPTO
        Alliance MPTO = new Alliance(
                UUID.randomUUID(),
                "상호방위조약기구",
                SERVER_CURRENCY,
                JBE
        );

        MPTO.setRepresentative(halliday);
        MPTO.addMember(JB_PRINCIPALITY);
        MPTO.getAssets().add(new CashStack(SERVER_CURRENCY, 10000000L));

        state.addActor(MPTO);
    }
}
