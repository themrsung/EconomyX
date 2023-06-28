package oasis.economyx.trading.market;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.asset.contract.Contract;
import oasis.economyx.state.EconomyState;
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
    @JsonProperty("uniqueId")
    UUID getUniqueId();

    /**
     * Gets the sender of the order
     */
    @JsonProperty("sender")
    Actor getSender();

    /**
     * Gets the type of this order
     */
    @JsonProperty("orderType")
    OrderType getOrderType();

    /**
     * Changes the type of this order
     * @param type New type
     * @throws IllegalArgumentException When order cannot be changed to the new type
     */
    @JsonIgnore
    void setOrderType(OrderType type) throws IllegalArgumentException;

    /**
     * Whether this is a buy order or not
     */
    @JsonIgnore
    default boolean isBuy() {
        return getOrderType().isBuy();
    }

    /**
     * Whether this is a market order or not
     */
    @JsonIgnore
    default boolean isMarket() {
        return getOrderType().isMarket();
    }

    /**
     * Whether order is immediate or not
     */
    @JsonIgnore
    default boolean isImmediate() {
        return getOrderType().isImmediate();
    }

    /**
     * Whether order allows partial fulfillment
     */
    @JsonIgnore
    default boolean allowsPartialFulfillment() {
        return getOrderType().allowsPartialFulfillment();
    }

    /**
     * Gets the time of order creation
     */
    @JsonProperty("time")
    DateTime getTime();

    /**
     * Gets the bid/ask price of this order
     */
    @NonNull
    @JsonProperty("price")
    CashStack getPrice();

    /**
     * Changes the price of this order
     */
    @JsonIgnore
    void setPrice(@NonNull CashStack price);

    /**
     * Gets the quantity of this order
     */
    @JsonProperty("quantity")
    long getQuantity();

    /**
     * Gets the collateral used to submit this order
     * Set to null for collateral-less orders
     */
    @Nullable
    @JsonProperty("colateral")
    Contract getCollateral();

    /**
     * Called on order submission
     * Handles contract registration by default
     * @param state Current working state of LedgerX
     */
    @JsonIgnore
    void onSubmitted(EconomyState state);

    /**
     * Called on order fulfillment
     * Handles internal order processing
     * @param state Current working state of LedgerX
     * @param price Price of fulfillment
     * @param quantity Quantity of fulfillment
     */
    @JsonIgnore
    void onFulfilled(EconomyState state, @NonNull CashStack price, @NonNegative long quantity);

    /**
     * Called on order cancellation
     * Safely cancels the order
     * @param state Current working state of LedgerX
     */
    @JsonIgnore
    void onCancelled(EconomyState state);

}
