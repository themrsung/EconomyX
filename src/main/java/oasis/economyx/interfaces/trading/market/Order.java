package oasis.economyx.interfaces.trading.market;

import oasis.economyx.actor.Actor;
import oasis.economyx.actor.types.finance.Brokerage;
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
     * @return Unique ID
     */
    UUID getUniqueId();

    /**
     * Gets the broker responsible for handling this order
     * @return Broker
     */
    Brokerage getBroker();

    /**
     * Gets the sender of the order
     * @return Sender
     */
    Actor getSender();

    /**
     * Checks if this an order sent by the broker
     * Agent trades have priority over proprietary orders
     * @return Whether this order was sent by the broker themselves
     */
    default boolean isProprietaryOrder() {
        return getBroker().equals(getSender());
    }

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
     * @return Whether this is a buy order
     */
    default boolean isBuy() {
        return getOrderType().isBuy();
    }

    /**
     * Whether this is a market order or not
     * @return Whether this is a market order
     */
    default boolean isMarket() {
        return getOrderType().isMarket();
    }

    /**
     * Whether order is immediate or not
     * @return Whether this is an immediate order
     */
    default boolean isImmediate() {
        return getOrderType().isImmediate();
    }

    /**
     * Whether order allows partial fulfillment
     * @return Whether this is order allows partial fulfillment
     */
    default boolean allowsPartialFulfillment() {
        return getOrderType().allowsPartialFulfillment();
    }

    /**
     * Gets the time of order creation
     * @return Order time
     */
    DateTime getTime();

    /**
     * Gets the bid/ask price of this order
     * @return Price
     */
    @NonNull
    CashStack getPrice();

    /**
     * Changes the price of this order
     */
    void setPrice(@NonNull CashStack price);

    /**
     * Gets the quantity of this order
     * @return Quantity
     */
    long getQuantity();

    /**
     * Gets the collateral used to submit this order
     * Set to null for collateral-less orders
     * @return Collateral if there is one, null if not
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
