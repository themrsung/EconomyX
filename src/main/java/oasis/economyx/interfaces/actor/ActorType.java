package oasis.economyx.interfaces.actor;

/**
 * Type of final actor instance
 */
public enum ActorType {
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
    TRUST;
}
