package oasis.economyx.asset.contract.option;

public enum OptionType {
    /**
     * A call option that can be exercised prematurely
     */
    AMERICAN_CALL,

    /**
     * A put option that can be exercised prematurely
     */
    AMERICAN_PUT,

    /**
     * A call option that can only be exercised on expiry
     */
    EUROPEAN_CALL,

    /**
     * A put option that can only be exercised on expiry
     */
    EUROPEAN_PUT;
}
