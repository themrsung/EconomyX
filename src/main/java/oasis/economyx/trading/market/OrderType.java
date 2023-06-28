package oasis.economyx.trading.market;

public enum OrderType {
    /**
     * Will only be fulfilled is price is lower than bid
     */
    LIMIT_BUY,

    /**
     * Will only be fulfilled is price is higher than bid
     */
    LIMIT_SELL,

    /**
     * Will be fulfilled at any price
     */
    MARKET_BUY,

    /**
     * Will be fulfilled at any price
     */
    MARKET_SELL,

    /**
     * Becomes a trading buy order when price reached
     */
    STOP_LOSS_BUY,
    /**
     * Becomes a trading sell order when price reached
     */
    STOP_LOSS_SELL,

    /**
     * Becomes a limit buy order when price reached
     */
    STOP_LIMIT_BUY,
    /**
     * Becomes a limit sell order when price reached
     */
    STOP_LIMIT_SELL,

    /**
     * Partial fulfillment is not allowed
     */
    ALL_OR_NONE_BUY,

    /**
     * Partial fulfillment is not allowed
     */
    ALL_OR_NONE_SELL,

    /**
     * Order will be cancelled after 1 second if not fulfilled
     */
    IMMEDIATE_OR_CANCEL_BUY,
    /**
     * Order will be cancelled after 1 second if not fulfilled
     */
    IMMEDIATE_OR_CANCEL_SELL,

    /**
     * All or none + Immediate or cancel
     */
    FILL_OR_KILL_BUY,
    /**
     * All or none + Immediate or cancel
     */
    FILL_OR_KILL_SELL;

    public boolean isBuy() {
        return switch (this) {
            case LIMIT_BUY, MARKET_BUY, STOP_LOSS_BUY, STOP_LIMIT_BUY, ALL_OR_NONE_BUY, IMMEDIATE_OR_CANCEL_BUY, FILL_OR_KILL_BUY ->
                    true;
            default -> false;
        };

    }

    public boolean isMarket() {
        return switch (this) {
            case MARKET_BUY, MARKET_SELL -> true;
            default -> false;
        };

    }

    public boolean isImmediate() {
        return switch (this) {
            case IMMEDIATE_OR_CANCEL_BUY, IMMEDIATE_OR_CANCEL_SELL, FILL_OR_KILL_BUY, FILL_OR_KILL_SELL -> true;
            default -> false;
        };
    }

    public boolean allowsPartialFulfillment() {
        return switch (this) {
            case ALL_OR_NONE_BUY, ALL_OR_NONE_SELL, FILL_OR_KILL_BUY, FILL_OR_KILL_SELL -> false;
            default -> true;
        };

    }
}
