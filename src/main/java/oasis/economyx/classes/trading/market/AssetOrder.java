package oasis.economyx.classes.trading.market;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Brokerage;
import oasis.economyx.interfaces.actor.types.trading.MarketHost;
import oasis.economyx.interfaces.trading.market.Order;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.contract.collateral.CollateralStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * The instantiable class or Order
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)

public final class AssetOrder implements Order {
    /**
     * Creates a new order
     * @param uniqueId Unique ID of this order
     * @param broker Brokerage handling this order
     * @param sender Sender of this order
     * @param type Type this order
     * @param price Price of this order
     * @param quantity Quantity of this order
     * @param collateral Collateral of this order
     */
    public AssetOrder(UUID uniqueId, Brokerage broker, Actor sender, Type type, CashStack price, @NonNegative long quantity, CollateralStack collateral) {
        this.uniqueId = uniqueId;
        this.broker = broker;
        this.sender = sender;
        this.type = type;
        this.price = price;
        this.quantity = quantity;
        this.collateral = collateral;

        this.time = new DateTime();
    }

    public AssetOrder() {
        this.uniqueId = UUID.randomUUID();
        this.broker = null;
        this.sender = null;
        this.type = null;
        this.price = null;
        this.quantity = 0L;
        this.collateral = null;

        this.time = new DateTime();
    }

    public AssetOrder(AssetOrder other) {
        this.uniqueId = other.uniqueId;
        this.broker = other.broker;
        this.sender = other.sender;
        this.type = other.type;
        this.time = other.time;
        this.price = other.price;
        this.quantity = other.quantity;
        this.collateral = other.collateral;
    }

    private final UUID uniqueId;
    private final Brokerage broker;
    private final Actor sender;
    private Type type;
    private final DateTime time;
    private CashStack price;
    @NonNegative
    private final long quantity;
    private final CollateralStack collateral;

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public Brokerage getBroker() {
        return broker;
    }

    @Override
    public Actor getSender() {
        return sender;
    }

    @Override
    public Type getType() {
        return type;
    }

    @Override
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    public DateTime getTime() {
        return time;
    }

    @NotNull
    @Override
    public CashStack getPrice() {
        return new CashStack(price);
    }

    @Override
    public void setPrice(@NonNull CashStack price) {
        this.price = price;
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Nullable
    @Override
    public CollateralStack getCollateral() {
        return collateral;
    }

    @Override
    public void onSubmitted(MarketHost exchange) {
        if (collateral == null) return;
        exchange.getAssets().add(collateral);
    }

    @Override
    public void onFulfilled(MarketHost exchange, @NonNull CashStack price, @NonNegative long quantity) {
        // transfer assets
        // brokerage fees
        // exchange fees (paid by brokerage to exchange)
    }

    @Override
    public void onCancelled(MarketHost exchange) {
        // unregister collateral
        // remove order
        // TODO TODO TODO
    }
}
