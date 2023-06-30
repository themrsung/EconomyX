package oasis.economyx.interfaces.trading.market;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.classes.trading.market.AssetOrder;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Brokerage;
import oasis.economyx.interfaces.actor.types.trading.MarketHost;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.contract.collateral.CollateralStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * An order placed for an asset
 * Supports every asset and order type
 */
@JsonSerialize(as = AssetOrder.class)
@JsonDeserialize(as = AssetOrder.class)
public interface Order {
    /**
     * Gets the unique ID of this order.
     *
     * @return Unique ID
     */
    UUID getUniqueId();

    /**
     * Gets the broker responsible for handling this order.
     *
     * @return Broker
     */
    Brokerage getBroker();

    /**
     * Gets the sender of the order
     *
     * @return Sender
     */
    Actor getSender();

    /**
     * Checks if this an order sent by the broker.
     * Agent trades have priority over proprietary orders.
     *
     * @return Whether this order was sent by the broker themselves
     */
    default boolean isProprietaryOrder() {
        return getBroker().equals(getSender());
    }

    /**
     * Gets the type of this order.
     */
    Type getType();

    /**
     * Changes the type of this order.
     *
     * @param type New type
     * @throws IllegalArgumentException When order cannot be changed to the new type
     */
    void setType(Type type) throws IllegalArgumentException;

    /**
     * Whether this is a buy order or not.
     *
     * @return Whether this is a buy order
     */
    default boolean isBuy() {
        return getType().isBuy();
    }

    /**
     * Whether this is a market order or not.
     *
     * @return Whether this is a market order
     */
    default boolean isMarket() {
        return getType().isMarket();
    }

    /**
     * Whether order is immediate or not.
     *
     * @return Whether this is an immediate order
     */
    default boolean isImmediate() {
        return getType().isImmediate();
    }

    /**
     * Whether order allows partial fulfillment.
     *
     * @return Whether this is order allows partial fulfillment
     */
    default boolean allowsPartialFulfillment() {
        return getType().allowsPartialFulfillment();
    }

    /**
     * Gets the time of order creation.
     *
     * @return Order time
     */
    DateTime getTime();

    /**
     * Gets the bid/ask price of this order.
     *
     * @return Price
     */
    @NonNull
    CashStack getPrice();

    /**
     * Changes the price of this order.
     */
    void setPrice(@NonNull CashStack price);

    /**
     * Gets the quantity of this order.
     *
     * @return Quantity
     */
    @NonNegative
    long getQuantity();

    /**
     * Gets the collateral used to submit this order.
     * Set to null for collateral-less orders.
     *
     * @return Collateral if there is one, null if not
     */
    @Nullable
    CollateralStack getCollateral();

    /**
     * Called on order submission.
     * Handles contract registration by default.
     *
     * @param exchange The actor running this market
     */
    void onSubmitted(MarketHost exchange);

    /**
     * Called on order fulfillment.
     * Handles internal order processing.
     *
     * @param exchange The actor running this market
     * @param price    Price of fulfillment
     * @param quantity Quantity of fulfillment
     */
    void onFulfilled(MarketHost exchange, @NonNull CashStack price, @NonNegative long quantity);

    /**
     * Called on order cancellation.
     * Safely cancels the order.
     *
     * @param exchange The actor running this market
     */
    void onCancelled(MarketHost exchange);

    enum Type {
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
}
