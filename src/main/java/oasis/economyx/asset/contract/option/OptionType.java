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

    public boolean isCall() {
        switch (this) {
            case AMERICAN_CALL:
            case EUROPEAN_CALL:
                return true;
        }
        return false;
    }

    public boolean isAmerican() {
        switch (this) {
            case AMERICAN_CALL:
            case AMERICAN_PUT:
                return true;
        }

        return false;
    }
}
