package oasis.economyx.state;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.company.trading.AuctionCompany;
import oasis.economyx.classes.actor.company.trading.ExchangeCompany;
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
import oasis.economyx.interfaces.actor.types.governance.Democratic;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.actor.types.institutional.*;
import oasis.economyx.interfaces.actor.types.manufacturing.Distiller;
import oasis.economyx.interfaces.actor.types.manufacturing.Minter;
import oasis.economyx.interfaces.actor.types.manufacturing.Producer;
import oasis.economyx.interfaces.actor.types.manufacturing.Scientific;
import oasis.economyx.interfaces.actor.types.ownership.Private;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.interfaces.actor.types.services.*;
import oasis.economyx.interfaces.actor.types.sovereign.Federal;
import oasis.economyx.interfaces.actor.types.trading.AuctionHouse;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.actor.types.warfare.Faction;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.interfaces.guarantee.Guarantee;
import oasis.economyx.interfaces.physical.Banknote;
import oasis.economyx.interfaces.terminal.CardTerminal;
import oasis.economyx.interfaces.trading.PriceProvider;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.interfaces.trading.auction.Bid;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.interfaces.voting.Candidate;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.PhysicalAsset;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.portfolio.Portfolio;
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
     *
     * @return Plugin
     */
    EconomyX getEX();

    /**
     * Gets all actors currently present within this state.
     *
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
     *
     * @param actor Actor to add
     */
    void addActor(@NonNull Actor actor);

    /**
     * Removes an actor from this state.
     *
     * @param actor Actor to remove
     */
    void removeActor(@NonNull Actor actor);

    /**
     * Checks if person exists.
     *
     * @param uniqueId Unique ID of person
     * @return Whether person exists
     */
    boolean isPerson(UUID uniqueId);

    /**
     * Gets a person by ID.
     * Check if person exists with isPerson(UUID) first.
     *
     * @param uniqueId Unique ID of person
     * @return Person
     * @throws IllegalArgumentException When person cannot be found
     */
    @NonNull
    Person getPerson(UUID uniqueId) throws IllegalArgumentException;

    /**
     * Gets all physicalized assets in circulation.
     * See {@link PhysicalAsset}
     *
     * @return A copied list of physicalized assets
     */
    @NonNull
    List<PhysicalAsset> getPhysicalizedAssets();

    /**
     * Adds a physicalized asset.
     * @param asset Asset to add
     */
    void addPhysicalizedAsset(@NonNull PhysicalAsset asset);

    /**
     * Removes a physicalized asset.
     * @param asset Asset to remove
     */
    void removePhysicalizedAsset(@NonNull PhysicalAsset asset);

    /**
     * Gets burnt assets.
     * Assets get burnt when an in-game player dies while holding them.
     * @return A copied list of burnt assets
     */
    Portfolio getBurntAssets();

    // Actor type interface getters

    /**
     * Gets all corporations.
     * Final subtypes:
     * <ul>
     *     <li>{@link ExchangeCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.HoldingsCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.ConstructionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Bank}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Merchant}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.SecuritiesBroker}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Manufacturer}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.Mercenary}</li>
     *     <li>{@link AuctionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Distillery}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Guarantor}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.PaperMill}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.LawFirm}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.vaulting.VaultCompany}</li>
     * </ul>
     *
     * @return Corporations
     */
    List<Corporation> getCorporations();

    /**
     * Gets all funds.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.trust.Trust}</li>
     * </ul>
     *
     * @return Funds
     */
    List<Fund> getFunds();

    /**
     * Gets all organizations.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.organization.international.Alliance}</li>
     *     <li>{@link oasis.economyx.classes.actor.organization.personal.Party}</li>
     *     <li>{@link oasis.economyx.classes.actor.organization.corporate.Cartel}</li>
     * </ul>
     *
     * @return Organizations
     */
    List<Organization<?>> getOrganizations();

    /**
     * Gets all persons.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.person.NaturalPerson}</li>
     * </ul>
     *
     * @return Persons
     */
    List<Person> getPersons();

    /**
     * Gets all sovereignties.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.singular.Republic}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.singular.Principality}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.federal.Empire}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.federal.Federation}</li>
     * </ul>
     *
     * @return Sovereignties
     */
    List<Sovereign> getSovereigns();

    /**
     * Gets all employers.
     * Final subtypes:
     * <ul>
     *     <li>{@link ExchangeCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.HoldingsCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.ConstructionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Bank}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Merchant}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.SecuritiesBroker}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Manufacturer}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.Mercenary}</li>
     *     <li>{@link AuctionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Distillery}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Guarantor}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.PaperMill}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.LawFirm}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.vaulting.VaultCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Legislature}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.monetary.CentralBank}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.warfare.ResearchCenter}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.monetary.Mint}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Administration}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Judiciary}</li>
     * </ul>
     *
     * @return Employers
     */
    List<Employer> getEmployers();

    /**
     * Gets all bankers.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Bank}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.SecuritiesBroker}</li>
     * </ul>
     *
     * @return Bankers
     */
    List<Banker> getBankers();

    /**
     * Gets all brokerages.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.SecuritiesBroker}</li>
     * </ul>
     *
     * @return Brokerages
     */
    List<Brokerage> getBrokerages();

    /**
     * Gets all credibles.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Guarantor}</li>
     * </ul>
     *
     * @return Credibles
     */
    List<Credible> getCredibles();

    /**
     * Gets all representables.
     * Final subtypes:
     * <ul>
     *     <li>{@link ExchangeCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.HoldingsCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.ConstructionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Bank}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Merchant}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.SecuritiesBroker}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Manufacturer}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.Mercenary}</li>
     *     <li>{@link AuctionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Distillery}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Guarantor}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.PaperMill}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.LawFirm}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.vaulting.VaultCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Legislature}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.monetary.CentralBank}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.warfare.ResearchCenter}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Administration}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.warfare.Military}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Judiciary}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.monetary.Mint}</li>
     *     <li>{@link oasis.economyx.classes.actor.trust.Trust}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Legislature}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.singular.Republic}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.singular.Principality}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.federal.Empire}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.federal.Federation}</li>
     *     <li>{@link oasis.economyx.classes.actor.organization.international.Alliance}</li>
     *     <li>{@link oasis.economyx.classes.actor.organization.personal.Party}</li>
     *     <li>{@link oasis.economyx.classes.actor.organization.corporate.Cartel}</li>
     * </ul>
     *
     * @return Representables
     */
    List<Representable> getRepresentables();

    /**
     * Gets all administratives.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Administration}</li>
     * </ul>
     *
     * @return Administratives
     */
    List<Administrative> getAdministratives();

    /**
     * Gets all banknote issuers.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.institution.monetary.Mint}</li>
     * </ul>
     *
     * @return Banknote issuers
     */
    List<BanknoteIssuer> getBanknoteIssuers();

    /**
     * Gets all currency issuers.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.institution.monetary.CentralBank}</li>
     * </ul>
     *
     * @return Currency issuers
     */
    List<CurrencyIssuer> getCurrencyIssuers();

    /**
     * Gets all institutionals.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Legislature}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.monetary.CentralBank}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.warfare.ResearchCenter}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Administration}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.warfare.Military}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Judiciary}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.monetary.Mint}</li>
     * </ul>
     *
     * @return Institutionals
     */
    List<Institutional> getInstitutionals();

    /**
     * Gets all interest rate providers.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Bank} (always positive)</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.SecuritiesBroker} (always returns 0%)</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.monetary.CentralBank}</li>
     * </ul>
     *
     * @return Interest rate providers
     */
    List<InterestRateProvider> getInterestRateProviders();

    /**
     * Gets all judicials.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Judiciary}</li>
     * </ul>
     *
     * @return Judicials
     */
    List<Judicial> getJudicials();

    /**
     * Gets all legislatives.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Legislature}</li>
     * </ul>
     *
     * @return Legislatives
     */
    List<Legislative> getLegislatives();

    /**
     * Gets all bill creators.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.common.PaperMill}</li>
     * </ul>
     *
     * @return Bill creators
     */
    List<Minter> getBillCreators();

    /**
     * Gets all distillers.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Distillery}</li>
     * </ul>
     *
     * @return Distillers
     */
    List<Distiller> getDistillers();

    /**
     * Gets all producers.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Manufacturer}</li>
     * </ul>
     *
     * @return Producers
     */
    List<Producer> getProducers();

    /**
     * Gets all scientifics.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.institution.warfare.ResearchCenter}</li>
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
     *     <li>{@link ExchangeCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.HoldingsCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.ConstructionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Bank}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Merchant}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.SecuritiesBroker}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Manufacturer}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.Mercenary}</li>
     *     <li>{@link AuctionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Distillery}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Guarantor}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.PaperMill}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.LawFirm}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.vaulting.VaultCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.trust.Trust}</li>
     * </ul>
     *
     * @return Shareds
     */
    List<Shared> getShareds();

    /**
     * Gets all builders.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.common.ConstructionCompany}</li>
     * </ul>
     *
     * @return Builders
     */
    List<Builder> getBuilders();

    /**
     * Gets all card acceptors.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Merchant}</li>
     * </ul>
     *
     * @return Card acceptors
     */
    List<CardAcceptor> getCardAccpetors();

    /**
     * Gets all factions.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.special.Mercenary}</li>
     *     <li>{@link oasis.economyx.classes.actor.organization.international.Alliance}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.warfare.Military}</li>
     * </ul>
     *
     * @return Factions
     */
    List<Faction> getFactions();

    /**
     * Gets all legals.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.special.LawFirm}</li>
     * </ul>
     *
     * @return Legals
     */
    List<Legal> getLegals();

    /**
     * Gets all protectors.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.singular.Republic}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.singular.Principality}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.federal.Empire}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.federal.Federation}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.Mercenary}</li>
     * </ul>
     *
     * @return Protectors
     */
    List<PropertyProtector> getProtectors();

    /**
     * Gets all vault keepers.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.vaulting.VaultCompany}</li>
     * </ul>
     *
     * @return Vault keepers
     */
    List<VaultKeeper> getVaultKeepers();

    /**
     * Gets all federals.
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.federal.Empire}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.federal.Federation}</li>
     * </ul>
     *
     * @return Federals
     */
    List<Federal> getFederals();

    /**
     * Gets all auction hosts.
     * <ul>
     *     <li>{@link AuctionCompany}</li>
     * </ul>
     *
     * @return Auction hosts
     */
    List<AuctionHouse> getAuctionHosts();

    /**
     * Gets all market hosts.
     * Final subtypes:
     * <ul>
     *     <li>{@link ExchangeCompany}</li>
     * </ul>
     *
     * @return Market hosts
     */
    List<Exchange> getMarketHosts();

    /**
     * Gets all card issuers.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Bank}</li>
     * </ul>
     *
     * @return Card issuers
     */
    List<CardIssuer> getCardIssuers();

    /**
     * Gets all card terminals.
     *
     * @return Card terminals
     */
    List<CardTerminal> getCardTerminals();

    /**
     * Gets all democratics.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.singular.Republic}</li>
     *     <li>{@link ExchangeCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.HoldingsCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.ConstructionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Bank}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Merchant}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.SecuritiesBroker}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Manufacturer}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.Mercenary}</li>
     *     <li>{@link AuctionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Distillery}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Guarantor}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.PaperMill}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.LawFirm}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.vaulting.VaultCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.institution.tripartite.Legislature}</li>
     *     <li>{@link oasis.economyx.classes.actor.sovereignty.federal.Federation}</li>
     * </ul>
     *
     * @return Democratics
     */
    List<Democratic> getDemocratics();

    // Other interface getters

    /**
     * Gets all accounts.
     *
     * @return Accounts
     */
    List<Account> getAccounts();

    /**
     * Gets all guarantees.
     *
     * @return Guarantees
     */
    List<Guarantee> getGuarantees();

    /**
     * Gets all banknotes.
     *
     * @return Banknotes
     */
    List<Banknote> getBanknotes();

    /**
     * Gets all open auctions.
     *
     * @return Auctioneers
     */
    List<Auctioneer> getAuctioneers();

    /**
     * Gets all bids in auctions.
     *
     * @return Bids
     */
    List<Bid> getBids();

    /**
     * Gets all open markets.
     *
     * @return Marketplaces
     */
    List<Marketplace> getMarketplaces();

    /**
     * Gets all orders in markets.
     *
     * @return Orders
     */
    List<Order> getOrders();

    /**
     * Gets all price providers.
     * Subinterfaces:
     * <ul>
     *     <li>{@link Marketplace}</li>
     *     <li>{@link Auctioneer}</li>
     * </ul>
     *
     * @return Prodivers
     */
    List<PriceProvider> getPriceProviders();

    /**
     * Gets all vault blocks.
     *
     * @return Vaults
     */
    List<VaultBlock> getVaultBlocks();

    /**
     * Gets every portfolio of every actor.
     * This a DIRECT pointer, not a copy.
     *
     * @return Portfolios
     */
    List<Portfolio> getPortfolios();

    /**
     * Gets every asset stack in every portfolio.
     * Only assets directly owned by an actor will be returns, as derivative assets are pointers to the original.
     * This is a DIRECT pointer, not a copy.
     *
     * @return Assets
     */
    List<AssetStack> getAssets();

    /**
     * Gets every active card.
     *
     * @return Cards
     */
    List<Card> getCards();

    /**
     * Gets all open votes.
     *
     * @return Votes
     */
    List<Vote> getVotes();

    /**
     * Gets all candidates in all votes.
     *
     * @return Candidates
     */
    List<Candidate> getCandidates();

    /**
     * Gets all voters in all votes.
     * Note that voters are not an instance of Actor.
     *
     * @return Voters
     */
    List<Voter> getVoters();

    /**
     * Gets all currencies in circulation.
     *
     * @return Currencies
     */
    List<Cash> getCurrencies();

    /**
     * Attempts to save
     */
    void save();
}
