package oasis.economyx.trading.auction;

public enum AuctionType {
    /**
     * A public auction where the highest bidder is chosen
     */
    ENGLISH,

    /**
     * A public auction where the first bidder is chosen
     */
    DUTCH,

    /**
     * A sealed auction where the highest bidder is chosen
     */
    FIRST_PRICE_SEALED,

    /**
     * A sealed auction where the highest bidder pays the price os the second-highest bidder
     */
    SECOND_PRICE_SEALED;
}
