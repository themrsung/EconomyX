package oasis.economyx.trading;

public enum PriceProviderType {
    // Markets

    /**
     * A market where orders compete to form a fair price
     */
    MARKET,

    // Auctions

    /**
     * A public auction where the highest bidder is chosen
     */
    ENGLISH_AUCTION,

    /**
     * A public auction where the first bidder is chosen
     */
    DUTCH_AUCTION,

    /**
     * A sealed auction where the highest bidder is chosen
     */
    FIRST_PRICE_AUCTION,

    /**
     * A sealed auction where the highest bidder pays the price os the second-highest bidder
     */
    SECOND_PRICE_AUCTION;
}
