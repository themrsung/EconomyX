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
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.interfaces.gaming.Table;
import oasis.economyx.interfaces.guarantee.Guarantee;
import oasis.economyx.interfaces.physical.Banknote;
import oasis.economyx.interfaces.trading.PriceProvider;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.interfaces.trading.auction.Bid;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.types.portfolio.Portfolio;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

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

    void addActor(@NonNull Actor actor);

    void removeActor(@NonNull Actor actor);

    // Actor type interface getters

    /**
     * Gets all corporations.
     * Final subtypes:
     * <ul>
     *     Exchange,
     *     HoldingsCompany,
     *     ConstructionCompany,
     *     Bank,
     *     Casino,
     *     Merchant,
     *     SecuritiesBroker,
     *     Manufacturer,
     *     Mercenary,
     *     AuctionHouse,
     *     Distillery,
     *     Guarantor,
     *     PaperMill,
     *     LawFirm,
     *     VaultCompany
     * </ul>
     *
     * @return Corporations
     */
    List<Corporation> getCorporations();

    /**
     * Gets all funds.
     * Final subtypes:
     * <ul>
     *     Trust
     * </ul>
     * @return Funds
     */
    List<Fund> getFunds();

    /**
     * Gets all organizations.
     * Final subtypes:
     * <ul>
     *     Alliance,
     *     Party,
     *     Cartel
     * </ul>
     *
     * @return Organizations
     */
    List<Organization<?>> getOrganizations();

    /**
     * Gets all persons.
     * Final subtypes:
     * <ul>
     *     NaturalPerson
     * </ul>
     *
     * @return Persons
     */
    List<Person> getPersons();

    /**
     * Gets all sovereignties.
     * Final subtypes:
     * <ul>
     *     Republic,
     *     Principality,
     *     Empire,
     *     Federation
     * </ul>
     *
     * @return Sovereignties
     */
    List<Sovereign> getSovereigns();

    /**
     * Gets all employers.
     * Final subtypes:
     * <ul>
     *     Exchange,
     *     HoldingsCompany,
     *     ConstructionCompany,
     *     Bank,
     *     Casino,
     *     Merchant,
     *     SecuritiesBroker,
     *     Manufacturer,
     *     Mercenary,
     *     AuctionHouse,
     *     Distillery,
     *     Guarantor,
     *     PaperMill,
     *     LawFirm,
     *     VaultCompany,
     *     Legislature,
     *     CentralBank,
     *     ResearchCenter,
     *     Mint,
     *     Administration,
     *     Judiciary
     * </ul>
     *
     * @return Employers
     */
    List<Employer> getEmployers();

    /**
     * Gets all bankers.
     * Final subtypes:
     * <ul>
     *     Bank,
     *     SecuritiesBroker
     * </ul>
     *
     * @return Bankers
     */
    List<Banker> getBankers();

    /**
     * Gets all brokerages.
     * Final subtypes:
     * <ul>
     *     SecuritiesBroker
     * </ul>
     *
     * @return Brokerages
     */
    List<Brokerage> getBrokerages();

    /**
     * Gets all credibles.
     * Final subtypes:
     * <ul>
     *     Guarantor
     * </ul>
     *
     * @return Credibles
     */
    List<Credible> getCredibles();

    /**
     * Gets all representables.
     * Final subtypes:
     * <ul>
     *     Exchange,
     *     HoldingsCompany,
     *     ConstructionCompany,
     *     Bank,
     *     Casino,
     *     Merchant,
     *     SecuritiesBroker,
     *     Manufacturer,
     *     Mercenary,
     *     AuctionHouse,
     *     Distillery,
     *     Guarantor,
     *     PaperMill,
     *     LawFirm,
     *     VaultCompany,
     *     Legislature,
     *     CentralBank,
     *     ResearchCenter,
     *     Administration,
     *     Military,
     *     Judiciary,
     *     Mint,
     *     Trust,
     *     Legislature,
     *     Republic,
     *     Principality,
     *     Empire,
     *     Federation,
     *     Alliance,
     *     Party,
     *     Cartel
     * </ul>
     *
     * @return Representable
     */
    List<Representable> getRepresentables();

    /**
     * Gets all administratives.
     * Final subtypes:
     * <ul>
     *     Administration
     * </ul>
     *
     * @return Administratives
     */
    List<Administrative> getAdministratives();

    /**
     * Gets all banknote issuers.
     * Final subtypes:
     * <ul>
     *     Mint
     * </ul>
     *
     * @return Banknote issuers
     */
    List<BanknoteIssuer> getBanknoteIssuers();

    /**
     * Gets all currency issuers.
     * Final subtypes:
     * <ul>
     *     CentralBank
     * </ul>
     *
     * @return Currency issuers
     */
    List<CurrencyIssuer> getCurrencyIssuers();

    /**
     * Gets all institutionals.
     * Final subtypes:
     * <ul>
     *     Legislature,
     *     CentralBank,
     *     ResearchCenter,
     *     Administration,
     *     Military,
     *     Judiciary,
     *     Mint
     * </ul>
     *
     * @return Institutionals
     */
    List<Institutional> getInstitutionals();

    /**
     * Gets all interest rate providers.
     * Final subtypes:
     * <ul>
     *     Bank (always positive),
     *     SecuritiesBroker (always returns 0%),
     *     CentralBank
     * </ul>
     *
     * @return Interest rate providers
     */
    List<InterestRateProvider> getInterestRateProviders();

    /**
     * Gets all judicials.
     * Final subtypes:
     * <ul>
     *     Judiciary
     * </ul>
     *
     * @return Judicials
     */
    List<Judicial> getJudicials();

    /**
     * Gets all legislatives.
     * Final subtypes:
     * <ul>
     *     Legislature
     * </ul>
     *
     * @return Legislatives
     */
    List<Legislative> getLegislatives();

    /**
     * Gets all bill creators.
     * Final subtypes:
     * <ul>
     *     PaperMill
     * </ul>
     *
     * @return Bill creators
     */
    List<BillCreator> getBillCreators();

    /**
     * Gets all distillers.
     * Final subtypes:
     * <ul>
     *     Distillery
     * </ul>
     *
     * @return Distillers
     */
    List<Distiller> getDistillers();

    /**
     * Gets all producers.
     * Final subtypes:
     * <ul>
     *     Manufacturer
     * </ul>
     *
     * @return Producers
     */
    List<Producer> getProducers();

    /**
     * Gets all scientifics.
     * Final subtypes:
     * <ul>
     *     ResearchCenter
     * </ul>
     *
     * @return Scientifics
     */
    List<Scientific> getScientifics();

    /**
     * Gets all privates.
     * Final subtypes:
     * <ul>
     *
     * </ul>
     *
     * @return Privates
     */
    List<Private> getPrivates();

    /**
     * Gets all shareds.
     * Final subtypes:
     * <ul>
     *     Exchange,
     *     HoldingsCompany,
     *     ConstructionCompany,
     *     Bank,
     *     Casino,
     *     Merchent,
     *     SecuritiesBroker,
     *     Manufacturer,
     *     Mercenary,
     *     AuctionHouse,
     *     Distillery,
     *     Guarantor,
     *     PaperMill,
     *     LawFirm,
     *     VaultCompany,
     *     Trust
     * </ul>
     *
     * @return Shareds
     */
    List<Shared> getShareds();

    /**
     * Gets all builders.
     * Final subtypes:
     * <ul>
     *     ConstructionCompany
     * </ul>
     *
     * @return Builders
     */
    List<Builder> getBuilders();

    /**
     * Gets all card acceptors.
     * Final subtypes:
     * <ul>
     *     Merchant
     * </ul>
     *
     * @return Card acceptors
     */
    List<CardAcceptor> getCardAccpetors();

    /**
     * Gets all factions.
     * Final subtypes:
     * <ul>
     *     Mercenary,
     *     Alliance,
     *     Military (Institution of Sovereign)
     * </ul>
     *
     * @return Factions
     */
    List<Faction> getFactions();

    /**
     * Gets all houses.
     * Final subtypes:
     * <ul>
     *     Casino
     * </ul>
     *
     * @return Houses
     */
    List<House> getHouses();

    /**
     * Gets all legals.
     * Final subtypes:
     * <ul>
     *     LawFirm
     * </ul>
     *
     * @return Legals
     */
    List<Legal> getLegals();

    /**
     * Gets all protectors.
     * Final subtypes:
     * <ul>
     *     Republic,
     *     Principality,
     *     Empire,
     *     Federation,
     *     Mercenary
     * </ul>
     * @return Protectors
     */
    List<Protector> getProtectors();

    /**
     * Gets all vault keepers.
     * Final subtypes:
     * <ul>
     *     VaultCompany
     * </ul>
     *
     * @return Vault keepers
     */
    List<VaultKeeper> getVaultKeepers();

    /**
     * Gets all federals.
     * <ul>
     *     Empire,
     *     Federation
     * </ul>
     *
     * @return Federals
     */
    List<Federal> getFederals();

    /**
     * Gets all auction hosts.
     * <ul>
     *     AuctionHouse
     * </ul>
     *
     * @return Auction hosts
     */
    List<AuctionHost> getAuctionHosts();

    /**
     * Gets all market hosts.
     * Final subtypes:
     * <ul>
     *     Exchange
     * </ul>
     *
     * @return Market hosts
     */
    List<MarketHost> getMarketHosts();

    /**
     * Gets all card issuers.
     * Final subtypes:
     * <ul>
     *     Bank
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
