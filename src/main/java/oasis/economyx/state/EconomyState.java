package oasis.economyx.state;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.fund.Fund;
import oasis.economyx.interfaces.actor.organization.Organization;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.actor.types.finance.Brokerage;
import oasis.economyx.interfaces.actor.types.finance.CardIssuer;
import oasis.economyx.interfaces.actor.types.finance.Credible;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.actor.types.institutional.*;
import oasis.economyx.interfaces.actor.types.manufacturing.BillCreator;
import oasis.economyx.interfaces.actor.types.manufacturing.Distiller;
import oasis.economyx.interfaces.actor.types.manufacturing.Producer;
import oasis.economyx.interfaces.actor.types.manufacturing.Scientific;
import oasis.economyx.interfaces.actor.types.ownership.Private;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.interfaces.actor.types.services.*;
import oasis.economyx.interfaces.actor.types.sovereign.Federal;
import oasis.economyx.interfaces.actor.types.trading.AuctionHost;
import oasis.economyx.interfaces.actor.types.trading.MarketHost;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.interfaces.gaming.Table;
import oasis.economyx.interfaces.guarantee.Guarantee;
import oasis.economyx.interfaces.physical.Banknote;
import oasis.economyx.interfaces.trading.PriceProvider;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.interfaces.trading.auction.Bid;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.portfolio.Portfolio;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * The state of EconomyX.
 * Every instance of every type can be accessed through this state.
 * There are getters for every interface type.
 */
public interface EconomyState {
    /**
     * Gets the plugin instance.
     * @return Plugin
     */
    EconomyX getEX();

    /**
     * Shortcut getter for logger.
     * @return Logger
     */
    default Logger getLogger() {
        return getEX().getLogger();
    }

    /**
     * Gets all actors currently present within this state.
     * @return Copied list of actors
     */
    List<Actor> getActors();

    /**
     * Checks if actor exists.
     *
     * @param uniqueId Unique ID of actor
     * @return Whether actor exists
     */
    boolean isActor(UUID uniqueId);

    /**
     * Gets actor by unique ID.
     * Check if actor exists with isActor(UUID) first.
     *
     * @param uniqueId Unique ID of actor
     * @return Actor
     * @throws IllegalArgumentException When actor cannot be found
     */
    @NonNull
    Actor getActor(UUID uniqueId) throws IllegalArgumentException;

    /**
     * Adds an actor to this state.
     * @param actor Actor to add
     */
    void addActor(@NonNull Actor actor);

    /**
     * Removes an actor from this state.
     * @param actor Actor to remove
     */
    void removeActor(@NonNull Actor actor);

    // Actor type interface getters

    /**
     * Gets all corporations.
     * Final subtypes:
     * <ul>
     *     <li>Exchange</li>
     *     <li>HoldingsCompany</li>
     *     <li>ConstructionCompany</li>
     *     <li>Bank</li>
     *     <li>Casino</li>
     *     <li>Merchant</li>
     *     <li>SecuritiesBroker</li>
     *     <li>Manufacturer</li>
     *     <li>Mercenary</li>
     *     <li>AuctionHouse</li>
     *     <li>Distillery</li>
     *     <li>Guarantor</li>
     *     <li>PaperMill</li>
     *     <li>LawFirm</li>
     *     <li>VaultCompany</li>
     * </ul>
     *
     * @return Corporations
     */
    List<Corporation> getCorporations();

    /**
     * Gets all funds.
     * Final subtypes:
     * <ul>
     *     <li>Trust</li>
     * </ul>
     * @return Funds
     */
    List<Fund> getFunds();

    /**
     * Gets all organizations.
     * Final subtypes:
     * <ul>
     *     <li>Alliance</li>
     *     <li>Party</li>
     *     <li>Cartel</li>
     * </ul>
     *
     * @return Organizations
     */
    List<Organization<?>> getOrganizations();

    /**
     * Gets all persons.
     * Final subtypes:
     * <ul>
     *     <li>NaturalPerson</li>
     * </ul>
     *
     * @return Persons
     */
    List<Person> getPersons();

    /**
     * Gets all sovereignties.
     * Final subtypes:
     * <ul>
     *     <li>Republic</li>
     *     <li>Principality</li>
     *     <li>Empire</li>
     *     <li>Federation</li>
     * </ul>
     *
     * @return Sovereignties
     */
    List<Sovereign> getSovereigns();

    /**
     * Gets all employers.
     * Final subtypes:
     * <ul>
     *     <li>Exchange</li>
     *     <li>HoldingsCompany</li>
     *     <li>ConstructionCompany</li>
     *     <li>Bank</li>
     *     <li>Casino</li>
     *     <li>Merchant</li>
     *     <li>SecuritiesBroker</li>
     *     <li>Manufacturer</li>
     *     <li>Mercenary</li>
     *     <li>AuctionHouse</li>
     *     <li>Distillery</li>
     *     <li>Guarantor</li>
     *     <li>PaperMill</li>
     *     <li>LawFirm</li>
     *     <li>VaultCompany</li>
     *     <li>Legislature</li>
     *     <li>CentralBank</li>
     *     <li>ResearchCenter</li>
     *     <li>Mint</li>
     *     <li>Administration</li>
     *     <li>Judiciary</li>
     * </ul>
     *
     * @return Employers
     */
    List<Employer> getEmployers();

    /**
     * Gets all bankers.
     * Final subtypes:
     * <ul>
     *     <li>>Bank</li>
     *     <li>SecuritiesBroker</li>
     * </ul>
     *
     * @return Bankers
     */
    List<Banker> getBankers();

    /**
     * Gets all brokerages.
     * Final subtypes:
     * <ul>
     *     <li>SecuritiesBroker</li>
     * </ul>
     *
     * @return Brokerages
     */
    List<Brokerage> getBrokerages();

    /**
     * Gets all credibles.
     * Final subtypes:
     * <ul>
     *     <li>Guarantor</li>
     * </ul>
     *
     * @return Credibles
     */
    List<Credible> getCredibles();

    /**
     * Gets all representables.
     * Final subtypes:
     * <ul>
     *     <li>Exchange</li>
     *     <li>HoldingsCompany</li>
     *     <li>ConstructionCompany</li>
     *     <li>Bank</li>
     *     <li>Casino</li>
     *     <li>Merchant</li>
     *     <li>SecuritiesBroker</li>
     *     <li>Manufacturer</li>
     *     <li>Mercenary</li>
     *     <li>AuctionHouse</li>
     *     <li>Distillery</li>
     *     <li>Guarantor</li>
     *     <li>PaperMill</li>
     *     <li>LawFirm</li>
     *     <li>VaultCompany</li>
     *     <li>Legislature</li>
     *     <li>CentralBank</li>
     *     <li>ResearchCenter</li>
     *     <li>Administration</li>
     *     <li>Military</li>
     *     <li>Judiciary</li>
     *     <li>Mint</li>
     *     <li>Trust</li>
     *     <li>Legislature</li>
     *     <li>Republic</li>
     *     <li>Principality</li>
     *     <li>Empire</li>
     *     <li>Federation</li>
     *     <li>Alliance</li>
     *     <li>Party</li>
     *     <li>Cartel</li>
     * </ul>
     *
     * @return Representables
     */
    List<Representable> getRepresentables();

    /**
     * Gets all administratives.
     * Final subtypes:
     * <ul>
     *     <li>Administration</li>
     * </ul>
     *
     * @return Administratives
     */
    List<Administrative> getAdministratives();

    /**
     * Gets all banknote issuers.
     * Final subtypes:
     * <ul>
     *     <li>Mint</li>
     * </ul>
     *
     * @return Banknote issuers
     */
    List<BanknoteIssuer> getBanknoteIssuers();

    /**
     * Gets all currency issuers.
     * Final subtypes:
     * <ul>
     *     <li>CentralBank</li>
     * </ul>
     *
     * @return Currency issuers
     */
    List<CurrencyIssuer> getCurrencyIssuers();

    /**
     * Gets all institutionals.
     * Final subtypes:
     * <ul>
     *     <li>Legislature</li>
     *     <li>CentralBank</li>
     *     <li>ResearchCenter</li>
     *     <li>Administration</li>
     *     <li>Military</li>
     *     <li>Judiciary</li>
     *     <li>Mint</li>
     * </ul>
     *
     * @return Institutionals
     */
    List<Institutional> getInstitutionals();

    /**
     * Gets all interest rate providers.
     * Final subtypes:
     * <ul>
     *     <li>Bank (always positive)</li>
     *     <li>SecuritiesBroker (always returns 0%)</li>
     *     <li>CentralBank</li>
     * </ul>
     *
     * @return Interest rate providers
     */
    List<InterestRateProvider> getInterestRateProviders();

    /**
     * Gets all judicials.
     * Final subtypes:
     * <ul>
     *     <li>Judiciary</li>
     * </ul>
     *
     * @return Judicials
     */
    List<Judicial> getJudicials();

    /**
     * Gets all legislatives.
     * Final subtypes:
     * <ul>
     *     <li>Legislature</li>
     * </ul>
     *
     * @return Legislatives
     */
    List<Legislative> getLegislatives();

    /**
     * Gets all bill creators.
     * Final subtypes:
     * <ul>
     *     <li>PaperMill</li>
     * </ul>
     *
     * @return Bill creators
     */
    List<BillCreator> getBillCreators();

    /**
     * Gets all distillers.
     * Final subtypes:
     * <ul>
     *     <li>Distillery</li>
     * </ul>
     *
     * @return Distillers
     */
    List<Distiller> getDistillers();

    /**
     * Gets all producers.
     * Final subtypes:
     * <ul>
     *     <li>Manufacturer</li>
     * </ul>
     *
     * @return Producers
     */
    List<Producer> getProducers();

    /**
     * Gets all scientifics.
     * Final subtypes:
     * <ul>
     *     <li>ResearchCenter</li>
     * </ul>
     *
     * @return Scientifics
     */
    List<Scientific> getScientifics();

    /**
     * Gets all privates.
     * Final subtypes:
     * <ul>
     *     <li>None</li>
     * </ul>
     *
     * @return Privates
     */
    List<Private> getPrivates();

    /**
     * Gets all shareds.
     * Final subtypes:
     * <ul>
     *     <li>Exchange</li>
     *     <li>HoldingsCompany</li>
     *     <li>ConstructionCompany</li>
     *     <li>Bank</li>
     *     <li>Casino</li>
     *     <li>Merchent</li>
     *     <li>SecuritiesBroker</li>
     *     <li>Manufacturer</li>
     *     <li>Mercenary</li>
     *     <li>AuctionHouse</li>
     *     <li>Distillery</li>
     *     <li>Guarantor</li>
     *     <li>PaperMill</li>
     *     <li>LawFirm</li>
     *     <li>VaultCompany</li>
     *     <li>Trust</li>
     * </ul>
     *
     * @return Shareds
     */
    List<Shared> getShareds();

    /**
     * Gets all builders.
     * Final subtypes:
     * <ul>
     *     <li>ConstructionCompany</li>
     * </ul>
     *
     * @return Builders
     */
    List<Builder> getBuilders();

    /**
     * Gets all card acceptors.
     * Final subtypes:
     * <ul>
     *     <li>Merchant</li>
     * </ul>
     *
     * @return Card acceptors
     */
    List<CardAcceptor> getCardAccpetors();

    /**
     * Gets all factions.
     * Final subtypes:
     * <ul>
     *     <li>Mercenary</li>
     *     <li>Alliance</li>
     *     <li>Military (Institution of Sovereign)</li>
     * </ul>
     *
     * @return Factions
     */
    List<Faction> getFactions();

    /**
     * Gets all houses.
     * Final subtypes:
     * <ul>
     *     <li>Casino</li>
     * </ul>
     *
     * @return Houses
     */
    List<House> getHouses();

    /**
     * Gets all legals.
     * Final subtypes:
     * <ul>
     *     <li>LawFirm</li>
     * </ul>
     *
     * @return Legals
     */
    List<Legal> getLegals();

    /**
     * Gets all protectors.
     * Final subtypes:
     * <ul>
     *     <li>Republic</li>
     *     <li>Principality</li>
     *     <li>Empire</li>
     *     <li>Federation</li>
     *     <li>Mercenary</li>
     * </ul>
     * @return Protectors
     */
    List<Protector> getProtectors();

    /**
     * Gets all vault keepers.
     * Final subtypes:
     * <ul>
     *     <li>VaultCompany</li>
     * </ul>
     *
     * @return Vault keepers
     */
    List<VaultKeeper> getVaultKeepers();

    /**
     * Gets all federals.
     * <ul>
     *     <li>Empire</li>
     *     <li>Federation</li>
     * </ul>
     *
     * @return Federals
     */
    List<Federal> getFederals();

    /**
     * Gets all auction hosts.
     * <ul>
     *     <li>AuctionHouse</li>
     * </ul>
     *
     * @return Auction hosts
     */
    List<AuctionHost> getAuctionHosts();

    /**
     * Gets all market hosts.
     * Final subtypes:
     * <ul>
     *     <li>Exchange</li>
     * </ul>
     *
     * @return Market hosts
     */
    List<MarketHost> getMarketHosts();

    /**
     * Gets all card issuers.
     * Final subtypes:
     * <ul>
     *     <li>Bank</li>
     * </ul>
     *
     * @return Card issuers
     */
    List<CardIssuer> getCardIssuers();

    // Other interface getters

    /**
     * Gets all accounts.
     * @return Accounts
     */
    List<Account> getAccounts();

    /**
     * Gets all casino tables.
     * @return Tables
     */
    List<Table> getTables();

    /**
     * Gets all guarantees.
     * @return Guarantees
     */
    List<Guarantee> getGuarantees();

    /**
     * Gets all banknotes.
     * @return Banknotes
     */
    List<Banknote> getBanknotes();

    /**
     * Gets all open auctions.
     * @return Auctioneers
     */
    List<Auctioneer> getAuctioneers();

    /**
     * Gets all bids in auctions.
     * @return Bids
     */
    List<Bid> getBids();

    /**
     * Gets all open markets.
     * @return Marketplaces
     */
    List<Marketplace> getMarketplaces();

    /**
     * Gets all orders in markets.
     * @return Orders
     */
    List<Order> getOrders();

    /**
     * Gets all price providers.
     * Final subtypes:
     * <ul>
     *     Market,
     *     EnglishAuction,
     *     DutchAuction,
     *     SecondPriceSealedAuction,
     *     FirstPriceSealedAuction
     * </ul>
     *
     * @return Prodivers
     */
    List<PriceProvider> getPriceProviders();

    /**
     * Gets all vault blocks.
     * @return Vaults
     */
    List<VaultBlock> getVaultBlocks();

    /**
     * Gets every portfolio of every actor.
     * This a DIRECT pointer, not a copy.
     * @return Portfolios
     */
    List<Portfolio> getPortfolios();

    /**
     * Gets every asset stack in every portfolio.
     * Only assets directly owned by an actor will be returns, as derivative assets are pointers to the original.
     * This is a DIRECT pointer, not a copy.
     * @return Assets
     */
    List<AssetStack> getAssets();

    /**
     * Gets every active card.
     * @return Cards
     */
    List<Card> getCards();

    /**
     * Attempts to save
     */
    void save();
}
