package oasis.economyx.interfaces.trading.market;

import oasis.economyx.actor.Actor;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.asset.contract.collateral.Collateral;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * An order placed for an asset
 * Supports every asset and order type
 */
public interface Order {
    /**
     * Gets the unique ID of this order
     */
    UUID getUniqueId();

    /**
     * Gets the sender of the order
     */
    Actor getSender();

    /**
     * Gets the type of this order
     */
    OrderType getOrderType();

    /**
     * Changes the type of this order
     * @param type New type
     * @throws IllegalArgumentException When order cannot be changed to the new type
     */
    void setOrderType(OrderType type) throws IllegalArgumentException;

    /**
     * Whether this is a buy order or not
     */
    default boolean isBuy() {
        return getOrderType().isBuy();
    }

    /**
     * Whether this is a trading order or not
     */
    default boolean isMarket() {
        return getOrderType().isMarket();
    }

    /**
     * Whether order is immediate or not
     */
    default boolean isImmediate() {
        return getOrderType().isImmediate();
    }

    /**
     * Whether order allows partial fulfillment
     */
    default boolean allowsPartialFulfillment() {
        return getOrderType().allowsPartialFulfillment();
    }

    /**
     * Gets the time of order creation
     */
    DateTime getTime();

    /**
     * Gets the bid/ask price of this order
     */
    @NonNull
    CashStack getPrice();

    /**
     * Changes the price of this order
     */
    void setPrice(@NonNull CashStack price);

    /**
     * Gets the quantity of this order
     */
    long getQuantity();

    /**
     * Gets the collateral used to submit this order
     * Set to null for collateral-less orders
     */
    @Nullable
    Collateral getCollateral();

    /**
     * Called on order submission
     * Handles contract registration by default
     * @param exchange The actor running this market
     */
    void onSubmitted(Actor exchange);

    /**
     * Called on order fulfillment
     * Handles internal order processing
     * @param exchange The actor running this market
     * @param price Price of fulfillment
     * @param quantity Quantity of fulfillment
     */
    void onFulfilled(Actor exchange, @NonNull CashStack price, @NonNegative long quantity);

    /**
     * Called on order cancellation
     * Safely cancels the order
     * @param exchange The actor running this market
     */
    void onCancelled(Actor exchange);

}
