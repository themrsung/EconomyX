package oasis.economyx.state;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.dataformat.yaml.YAMLFactory;
import com.fasterxml.jackson.datatype.joda.JodaModule;
import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.company.finance.Guarantor;
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
import oasis.economyx.interfaces.actor.types.gaming.House;
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
import oasis.economyx.interfaces.gaming.table.Table;
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
import oasis.economyx.types.portfolio.Portfolio;
import oasis.economyx.types.security.Sensitive;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
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

    // Actor type interface getters

    /**
     * Gets all corporations.
     * Final subtypes:
     * <ul>
     *     <li>{@link ExchangeCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.special.HoldingsCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.ConstructionCompany}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.finance.Bank}</li>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Casino}</li>
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
     *     <li>{@link oasis.economyx.classes.actor.company.common.Casino}</li>
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
     *     <li>{@link oasis.economyx.classes.actor.company.common.Casino}</li>
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
     *     <li>{@link oasis.economyx.classes.actor.company.common.Casino}</li>
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
     * Gets all houses.
     * Final subtypes:
     * <ul>
     *     <li>{@link oasis.economyx.classes.actor.company.common.Casino}</li>
     * </ul>
     *
     * @return Houses
     */
    List<House> getHouses();

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
     *     <li>{@link oasis.economyx.classes.actor.company.common.Casino}</li>
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
     * Gets all casino tables.
     *
     * @return Tables
     */
    List<Table> getTables();

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
     * Attempts to save
     */
    void save();

    // SECURITY

    /**
     * Gets a safe-to-tinker-with copy of this state.
     * This is a deep copy within the bounds of this application.
     *
     * @return Deep copy
     */
    default EconomyState copy() {
        return new ReadOnly(this);
    }

    /**
     * Gets a state from the perspective of a viewer.
     * This is safe to give to clients.
     * Nonnull fields in censored states are not guaranteed to be nonnull.
     *
     * @param viewer ORIGINAL instance of viewer; UUID does not guarantee access
     * @return Censored state
     */
    default EconomyState censor(@NonNull Person viewer) {
        EconomyState copy = copy();

        for (Actor a : copy.getActors()) {
            if (!Objects.equals(a, viewer)) {
                if (a instanceof Sensitive s) {
                    boolean hasAccess = false;

                    if (a instanceof Employer e) {
                        hasAccess = hasAccess || e.getEmployees().contains(viewer);
                        hasAccess = hasAccess || e.getDirectors().contains(viewer);
                    }

                    if (a instanceof Representable r) {
                        hasAccess = hasAccess || Objects.equals(r.getRepresentative(), viewer);
                    }

                    if (!hasAccess) s.nuke();
                }
            }
        }

        return copy;
    }


    class ReadOnly implements EconomyState {
        /**
         * Makes a deep copy.
         *
         * @param state State to copy
         */
        private ReadOnly(@NonNull EconomyState state) {
            this.actors = new ArrayList<>();

            for (Actor a : state.getActors()) {
                try {
                    ObjectMapper mapper = new ObjectMapper(new YAMLFactory()).registerModule(new JodaModule());

                    this.actors.add(mapper.readValue(
                            mapper.writeValueAsString(a), Actor.class
                    ));

                } catch (IOException e) {
                    throw new RuntimeException();
                }
            }
        }

        private final @NonNull List<Actor> actors;


        /**
         * Always returns null
         *
         * @return Null
         */
        @Override
        @Nullable
        public EconomyX getEX() {
            return null;
        }

        @Override
        public List<Actor> getActors() {
            return new ArrayList<>(actors);
        }

        @Override
        public boolean isActor(UUID uniqueId) {
            for (Actor a : getActors()) {
                if (a.getUniqueId().equals(uniqueId)) {
                    return true;
                }
            }

            return false;
        }

        @Override
        public @NonNull Actor getActor(UUID uniqueId) throws IllegalArgumentException {
            for (Actor a : getActors()) {
                if (a.getUniqueId().equals(uniqueId)) return a;
            }

            throw new IllegalArgumentException();
        }

        /**
         * This will only affect the copied instance
         *
         * @param actor Actor to add
         */
        @Override
        public void addActor(@NonNull Actor actor) {
            actors.add(actor);
        }

        /**
         * This will only affect the copied instance
         *
         * @param actor Actor to remove
         */
        @Override
        public void removeActor(@NonNull Actor actor) {
            actors.remove(actor);
        }

        @Override
        public boolean isPerson(UUID uniqueId) {
            for (Person p : getPersons()) {
                if (p.getUniqueId().equals(uniqueId)) return true;
            }

            return false;
        }

        @Override
        public @NonNull Person getPerson(UUID uniqueId) throws IllegalArgumentException {
            for (Person p : getPersons()) {
                if (p.getUniqueId().equals(uniqueId)) return p;
            }

            throw new IllegalArgumentException();
        }

        @Override
        public List<Corporation> getCorporations() {
            List<Corporation> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Corporation c) {
                    list.add(c);
                }
            }

            return list;
        }

        @Override
        public List<Fund> getFunds() {
            List<Fund> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Fund f) {
                    list.add(f);
                }
            }

            return list;
        }

        @Override
        public List<Organization<?>> getOrganizations() {
            List<Organization<?>> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Organization<?> o) list.add(o);
            }

            return list;
        }

        @Override
        public List<Person> getPersons() {
            List<Person> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Person p) list.add(p);
            }

            return list;
        }

        @Override
        public List<Employer> getEmployers() {
            List<Employer> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Employer e) list.add(e);
            }

            return list;
        }

        @Override
        public List<Banker> getBankers() {
            List<Banker> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Banker b) list.add(b);
            }

            return list;
        }

        @Override
        public List<Brokerage> getBrokerages() {
            List<Brokerage> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Brokerage b) list.add(b);
            }

            return list;
        }

        @Override
        public List<Credible> getCredibles() {
            List<Credible> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Credible c) list.add(c);
            }

            return list;
        }

        @Override
        public List<Representable> getRepresentables() {
            List<Representable> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Representable r) list.add(r);
            }

            return list;
        }

        @Override
        public List<Administrative> getAdministratives() {
            List<Administrative> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Administrative ad) list.add(ad);
            }

            return list;
        }

        @Override
        public List<Sovereign> getSovereigns() {
            List<Sovereign> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Sovereign s) list.add(s);
            }

            return list;
        }

        @Override
        public List<BanknoteIssuer> getBanknoteIssuers() {
            List<BanknoteIssuer> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof BanknoteIssuer bi) list.add(bi);
            }

            return list;
        }

        @Override
        public List<CurrencyIssuer> getCurrencyIssuers() {
            List<CurrencyIssuer> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof CurrencyIssuer ci) list.add(ci);
            }

            return list;
        }

        @Override
        public List<Institutional> getInstitutionals() {
            List<Institutional> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Institutional i) list.add(i);
            }

            return list;
        }

        @Override
        public List<InterestRateProvider> getInterestRateProviders() {
            List<InterestRateProvider> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof InterestRateProvider i) list.add(i);
            }

            return list;
        }

        @Override
        public List<Judicial> getJudicials() {
            List<Judicial> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Judicial j) list.add(j);
            }

            return list;
        }

        @Override
        public List<Legislative> getLegislatives() {
            List<Legislative> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Legislative l) list.add(l);
            }

            return list;
        }

        @Override
        public List<Minter> getBillCreators() {
            List<Minter> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Minter b) list.add(b);
            }

            return list;
        }

        @Override
        public List<Distiller> getDistillers() {
            List<Distiller> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Distiller d) list.add(d);
            }

            return list;
        }

        @Override
        public List<Producer> getProducers() {
            List<Producer> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Producer p) list.add(p);
            }

            return list;
        }

        @Override
        public List<Scientific> getScientifics() {
            List<Scientific> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Scientific s) list.add(s);
            }

            return list;
        }

        @Override
        public List<Private> getPrivates() {
            List<Private> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Private p) list.add(p);
            }

            return list;
        }

        @Override
        public List<Shared> getShareds() {
            List<Shared> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Shared s) list.add(s);
            }

            return list;
        }

        @Override
        public List<Builder> getBuilders() {
            List<Builder> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Builder b) list.add(b);
            }

            return list;
        }

        @Override
        public List<CardAcceptor> getCardAccpetors() {
            List<CardAcceptor> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof CardAcceptor c) list.add(c);
            }

            return list;
        }

        @Override
        public List<Faction> getFactions() {
            List<Faction> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Faction f) list.add(f);
            }

            return list;
        }

        @Override
        public List<House> getHouses() {
            List<House> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof House h) list.add(h);
            }

            return list;
        }

        @Override
        public List<Legal> getLegals() {
            List<Legal> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Legal l) list.add(l);
            }

            return list;
        }

        @Override
        public List<PropertyProtector> getProtectors() {
            List<PropertyProtector> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof PropertyProtector p) list.add(p);
            }

            return list;
        }

        @Override
        public List<VaultKeeper> getVaultKeepers() {
            List<VaultKeeper> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof VaultKeeper v) list.add(v);
            }

            return list;
        }

        @Override
        public List<Federal> getFederals() {
            List<Federal> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Federal f) list.add(f);
            }

            return list;
        }

        @Override
        public List<AuctionHouse> getAuctionHosts() {
            List<AuctionHouse> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof AuctionHouse ah) list.add(ah);
            }

            return list;
        }

        @Override
        public List<Exchange> getMarketHosts() {
            List<Exchange> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Exchange m) list.add(m);
            }

            return list;
        }

        @Override
        public List<CardIssuer> getCardIssuers() {
            List<CardIssuer> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof CardIssuer ci) list.add(ci);
            }

            return list;
        }

        @Override
        public List<CardTerminal> getCardTerminals() {
            List<CardTerminal> terminals = new ArrayList<>();

            for (CardAcceptor ca : getCardAccpetors()) {
                terminals.addAll(ca.getCardTerminals());
            }

            return terminals;
        }

        @Override
        public List<Democratic> getDemocratics() {
            List<Democratic> list = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Democratic d) list.add(d);
            }

            return list;
        }

        @Override
        public List<Account> getAccounts() {
            List<Account> accounts = new ArrayList<>();

            for (Banker b : getBankers()) {
                accounts.addAll(b.getAccounts());
            }

            return accounts;
        }

        @Override
        public List<Table> getTables() {
            List<Table> tables = new ArrayList<>();

            for (House h : getHouses()) {
                tables.addAll(h.getTables());
            }

            return tables;
        }

        @Override
        public List<Guarantee> getGuarantees() {
            List<Guarantee> guarantees = new ArrayList<>();

            for (Actor a : getActors()) {
                if (a instanceof Guarantor g) {
                    guarantees.addAll(g.getGuarantees());
                }
            }

            return guarantees;
        }

        @Override
        public List<Banknote> getBanknotes() {
            List<Banknote> notes = new ArrayList<>();

            for (BanknoteIssuer bi : getBanknoteIssuers()) {
                notes.addAll(bi.getIssuedBanknotes());
            }

            return notes;
        }

        @Override
        public List<Auctioneer> getAuctioneers() {
            List<Auctioneer> auctioneers = new ArrayList<>();

            for (AuctionHouse ah : getAuctionHosts()) {
                auctioneers.addAll(ah.getAuctions());
            }

            return auctioneers;
        }

        @Override
        public List<Bid> getBids() {
            List<Bid> bids = new ArrayList<>();

            for (Auctioneer a : getAuctioneers()) {
                bids.addAll(a.getBids());
            }

            return bids;
        }

        @Override
        public List<Marketplace> getMarketplaces() {
            List<Marketplace> markets = new ArrayList<>();

            for (Exchange h : getMarketHosts()) {
                markets.addAll(h.getMarkets());
            }

            return markets;
        }

        @Override
        public List<Order> getOrders() {
            List<Order> orders = new ArrayList<>();

            for (Marketplace m : getMarketplaces()) {
                orders.addAll(m.getOrders());
            }

            return orders;
        }

        @Override
        public List<PriceProvider> getPriceProviders() {
            List<PriceProvider> providers = new ArrayList<>();

            for (Exchange h : getMarketHosts()) {
                providers.addAll(h.getMarkets());
            }

            for (AuctionHouse h : getAuctionHosts()) {
                providers.addAll(h.getAuctions());
            }

            return providers;
        }

        @Override
        public List<VaultBlock> getVaultBlocks() {
            List<VaultBlock> vaults = new ArrayList<>();

            for (VaultKeeper k : getVaultKeepers()) {
                vaults.addAll(k.getVaults());
            }

            return vaults;
        }

        @Override
        public List<Portfolio> getPortfolios() {
            List<Portfolio> portfolios = new ArrayList<>();

            for (Actor a : getActors()) {
                portfolios.add(a.getAssets());
            }

            return portfolios;
        }

        /**
         * Searching within contracts is not necessary.
         */
        @Override
        public List<AssetStack> getAssets() {
            List<AssetStack> stacks = new ArrayList<>();

            for (Portfolio p : getPortfolios()) {
                stacks.addAll(p.get());
            }

            return stacks;
        }

        @Override
        public List<Card> getCards() {
            List<Card> cards = new ArrayList<>();

            for (CardIssuer ci : getCardIssuers()) {
                cards.addAll(ci.getIssuedCards());
            }

            return cards;
        }

        @Override
        public List<Vote> getVotes() {
            List<Vote> votes = new ArrayList<>();

            for (Democratic d : getDemocratics()) {
                votes.addAll(d.getOpenVotes());
            }

            return votes;
        }

        @Override
        public List<Candidate> getCandidates() {
            List<Candidate> candidates = new ArrayList<>();

            for (Vote v : getVotes()) {
                candidates.addAll(v.getCandidates());
            }

            return candidates;
        }

        @Override
        public List<Voter> getVoters() {
            List<Voter> voters = new ArrayList<>();

            for (Vote v : getVotes()) {
                voters.addAll(v.getVoters());
            }

            return voters;
        }

        /**
         * Does absolutely nothing.
         */
        @Override
        public void save() {
            // Do nothing
        }
    }
}
