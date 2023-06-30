package oasis.economyx.interfaces.actor;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import oasis.economyx.classes.actor.company.common.*;
import oasis.economyx.classes.actor.company.finance.Bank;
import oasis.economyx.classes.actor.company.finance.Guarantor;
import oasis.economyx.classes.actor.company.finance.SecuritiesBroker;
import oasis.economyx.classes.actor.company.special.HoldingsCompany;
import oasis.economyx.classes.actor.company.special.LawFirm;
import oasis.economyx.classes.actor.company.special.Mercenary;
import oasis.economyx.classes.actor.company.trading.AuctionHouse;
import oasis.economyx.classes.actor.company.trading.Exchange;
import oasis.economyx.classes.actor.company.vaulting.VaultCompany;
import oasis.economyx.classes.actor.institution.monetary.CentralBank;
import oasis.economyx.classes.actor.institution.monetary.Mint;
import oasis.economyx.classes.actor.institution.tripartite.Administration;
import oasis.economyx.classes.actor.institution.tripartite.Judiciary;
import oasis.economyx.classes.actor.institution.tripartite.Legislature;
import oasis.economyx.classes.actor.institution.warfare.Military;
import oasis.economyx.classes.actor.institution.warfare.ResearchCenter;
import oasis.economyx.classes.actor.organization.corporate.Cartel;
import oasis.economyx.classes.actor.organization.international.Alliance;
import oasis.economyx.classes.actor.organization.personal.Party;
import oasis.economyx.classes.actor.person.NaturalPerson;
import oasis.economyx.classes.actor.sovereignty.federal.Empire;
import oasis.economyx.classes.actor.sovereignty.federal.Federation;
import oasis.economyx.classes.actor.sovereignty.singular.Principality;
import oasis.economyx.classes.actor.sovereignty.singular.Republic;
import oasis.economyx.classes.actor.trust.Trust;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.portfolio.Portfolio;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * An actor is capable of holding assets and executing economic actions. (e.g. placing orders or bids)
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonSubTypes({
        // Corporations
        @JsonSubTypes.Type(value = Merchant.class, name = "MERCHANT"),
        @JsonSubTypes.Type(value = Manufacturer.class, name = "MANUFACTURER"),
        @JsonSubTypes.Type(value = PaperMill.class, name = "PAPER_MILL"),
        @JsonSubTypes.Type(value = ConstructionCompany.class, name = "CONSTRUCTION_COMPANY"),
        @JsonSubTypes.Type(value = Distillery.class, name = "DISTILLERY"),
        @JsonSubTypes.Type(value = Casino.class, name = "CASINO"),
        @JsonSubTypes.Type(value = Exchange.class, name = "EXCHANGE"),
        @JsonSubTypes.Type(value = AuctionHouse.class, name = "AUCTION_HOUSE"),
        @JsonSubTypes.Type(value = Bank.class, name = "BANK"),
        @JsonSubTypes.Type(value = SecuritiesBroker.class, name = "SECURITIES_BROKER"),
        @JsonSubTypes.Type(value = Guarantor.class, name = "GUARANTOR"),
        @JsonSubTypes.Type(value = VaultCompany.class, name = "VAULT_COMPANY"),
        @JsonSubTypes.Type(value = Mercenary.class, name = "MERCENARY"),
        @JsonSubTypes.Type(value = LawFirm.class, name = "LAW_FIRM"),
        @JsonSubTypes.Type(value = HoldingsCompany.class, name = "HOLDINGS_COMPANY"),

        // Persons
        @JsonSubTypes.Type(value = NaturalPerson.class, name = "NATURAL_PERSON"),

        // Sovereignties
        @JsonSubTypes.Type(value = Empire.class, name = "EMPIRE"),
        @JsonSubTypes.Type(value = Federation.class, name = "FEDERATION"),
        @JsonSubTypes.Type(value = Republic.class, name = "REPUBLIC"),
        @JsonSubTypes.Type(value = Principality.class, name = "PRINCIPALITY"),

        // Organizations
        @JsonSubTypes.Type(value = Alliance.class, name = "ALLIANCE"),
        @JsonSubTypes.Type(value = Cartel.class, name = "CARTEL"),
        @JsonSubTypes.Type(value = Party.class, name = "PARTY"),

        // Institutions
        @JsonSubTypes.Type(value = CentralBank.class, name = "CENTRAL_BANK"),
        @JsonSubTypes.Type(value = Mint.class, name = "MINT"),
        @JsonSubTypes.Type(value = ResearchCenter.class, name = "RESEARCH_CENTER"),
        @JsonSubTypes.Type(value = Military.class, name = "MILITARY"),
        @JsonSubTypes.Type(value = Administration.class, name = "ADMINISTRATION"),
        @JsonSubTypes.Type(value = Legislature.class, name = "LEGISLATURE"),
        @JsonSubTypes.Type(value = Judiciary.class, name = "JUDICIARY"),

        // Funds
        @JsonSubTypes.Type(value = Trust.class, name = "TRUST"),
})

public interface Actor {
    /**
     * Gets the unique ID of this actor.
     *
     * @return Unique ID
     */
    UUID getUniqueId();

    /**
     * Gets the semantic name of this actor.
     * Can be null and is not unique.
     *
     * @return Name
     */
    @Nullable
    String getName();

    /**
     * Sets the semantic name of this actor.
     *
     * @param name Can be null and does not require uniqueness
     */
    void setName(@Nullable String name);

    /**
     * Gets the gross holdings of this actor.
     *
     * @return Assets
     */
    Portfolio getAssets();

    /**
     * Gets all outstanding collaterals of this actor.
     *
     * @param state Current running state
     * @return Collaterals
     */
    Portfolio getOutstandingCollateral(EconomyState state);

    /**
     * Gets all outstanding liabilities of this actor. (is inclusive of collaterals)
     *
     * @param state Current running state
     * @return Liabilities
     */
    Portfolio getLiabilities(EconomyState state);

    /**
     * Gets all payable assets. (Gross assets subtracted by collaterals)
     * This is used to determine whether the actor can fulfill a payment.
     *
     * @param state Current running state
     * @return Payable assets.
     */
    Portfolio getPayableAssets(EconomyState state);

    /**
     * Gets the net assets of this actor. (Assets subtracted by liabilities)
     * This is used to check if the actor is insolvent.
     *
     * @param state Current running state
     * @return Net assets
     */
    Portfolio getNetAssets(EconomyState state);

    /**
     * Gets the type of this actor
     *
     * @return Type
     */
    Type getType();

    /**
     * Type of final actor instance
     */
    enum Type {
        // Corporations

        /**
         * Able to accept credit card payments
         */
        MERCHANT,

        /**
         * Able to mass produce goods
         */
        MANUFACTURER,

        /**
         * Able to create special paper (used for cash and securities)
         */
        PAPER_MILL,

        /**
         * Contractor
         */
        CONSTRUCTION_COMPANY,

        /**
         * Able to create alcohol-based beverages
         */
        DISTILLERY,

        /**
         * Able offer gambling services
         */
        CASINO,

        /**
         * Able to run markets
         */
        EXCHANGE,

        /**
         * Able to run auctions
         */
        AUCTION_HOUSE,

        /**
         * Able to handle deposits, withdrawals, transfers, and loans (via line of credit or collateralized loans)
         */
        BANK,

        /**
         * Able to hold securities and place orders on their clients' behalf
         */
        SECURITIES_BROKER,

        /**
         * Able to issue guarantees to other actors
         */
        GUARANTOR,

        /**
         * Able to hold commodities on their client's behalf
         */
        VAULT_COMPANY,

        /**
         * Able to meet clients' military needs
         */
        MERCENARY,

        /**
         * Able to represent clients in lawsuits
         */
        LAW_FIRM,

        /**
         * A generic company with no special abilities
         * Used to hold other companies' stock
         */
        HOLDINGS_COMPANY,

        // Persons

        /**
         * Person
         */
        NATURAL_PERSON,

        // Sovereignties

        /**
         * An autocratic collection of states
         */
        EMPIRE,

        /**
         * A democratic collection of states
         */
        FEDERATION,

        /**
         * A singular democratic state
         */
        REPUBLIC,

        /**
         * A singular autocratic state
         */
        PRINCIPALITY,

        // Organizations

        /**
         * An organization of sovereigns
         */
        ALLIANCE,

        /**
         * An organization of corporations
         */
        CARTEL,

        /**
         * An organization of people
         */
        PARTY,

        // Institutions

        /**
         * Can create issue a currency
         */
        CENTRAL_BANK,

        /**
         * Can issue banknotes
         */
        MINT,

        /**
         * Can produce WMDs and ICBMs
         */
        RESEARCH_CENTER,

        /**
         * Represents its parent sovereignty in warfare
         */
        MILITARY,

        /**
         * The administration of a sovereignty
         */
        ADMINISTRATION,

        /**
         * The legislature of a sovereignty
         */
        LEGISLATURE,

        /**
         * Represents the judicial power of a sovereignty
         */
        JUDICIARY,

        // Trusts

        /**
         * Fund
         */
        TRUST
    }
}
