package oasis.economyx.classes.trading.market;

import com.fasterxml.jackson.annotation.*;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Brokerage;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.contract.collateral.CollateralStack;
import org.bukkit.Bukkit;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * The instantiable class or Order
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)

public final class AssetOrder implements Order {
    /**
     * Creates a new order
     *
     * @param uniqueId   Unique ID of this order
     * @param market     Market this order was sent to
     * @param broker     Brokerage handling this order
     * @param sender     Sender of this order
     * @param type       Type this order
     * @param price      Price of this order
     * @param quantity   Quantity of this order
     * @param collateral Collateral of this order
     */
    public AssetOrder(UUID uniqueId, @NonNull Marketplace market, Brokerage broker, Actor sender, Type type, CashStack price, @NonNegative long quantity, CollateralStack collateral) {
        this.uniqueId = uniqueId;
        this.market = market;
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
        this.market = null;
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
        this.market = other.market;
        this.broker = other.broker;
        this.sender = other.sender;
        this.type = other.type;
        this.time = other.time;
        this.price = other.price;
        this.quantity = other.quantity;
        this.collateral = other.collateral;
    }

    @JsonProperty
    private final UUID uniqueId;
    @JsonProperty
    @JsonIdentityReference
    private final Marketplace market;
    @JsonProperty
    @JsonIdentityReference
    private final Brokerage broker;
    @JsonProperty
    @JsonIdentityReference
    private final Actor sender;
    @JsonProperty
    private Type type;
    @JsonProperty
    private DateTime time;
    @JsonProperty
    private CashStack price;
    @NonNegative
    @JsonProperty
    private long quantity;
    @JsonProperty
    @JsonIdentityReference
    private final CollateralStack collateral;

    @Override
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    @JsonIgnore
    public Marketplace getMarket() {
        return market;
    }

    @Override
    @JsonIgnore
    public Brokerage getBroker() {
        return broker;
    }

    @Override
    @JsonIgnore
    public Actor getSender() {
        return sender;
    }

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public void setType(Type type) {
        this.type = type;
    }

    @Override
    @JsonIgnore
    public DateTime getTime() {
        return time;
    }

    @NonNull
    @Override
    @JsonIgnore
    public CashStack getPrice() {
        return new CashStack(price);
    }

    @Override
    @JsonIgnore
    public void setPrice(@NonNull CashStack price) {
        this.price = price;
    }

    @Override
    @JsonIgnore
    public long getQuantity() {
        return quantity;
    }

    @Nullable
    @Override
    @JsonIgnore
    public CollateralStack getCollateral() {
        return collateral;
    }

    @Override
    @JsonIgnore
    public void onSubmitted(@NonNull Exchange exchange) {
        if (collateral == null) return;
        exchange.getAssets().add(collateral);
    }

    @Override
    @JsonIgnore
    public void onFulfilled(@NonNull Exchange exchange, @NonNull CashStack price, @NonNegative long quantity) {
        // Settle cash
        CashStack cash = price.multiply(quantity);

        if (isBuy()) {
            Bukkit.getPluginManager().callEvent(new PaymentEvent(
                    sender,
                    exchange,
                    cash,
                    PaymentEvent.Cause.ORDER_SETTLEMENT
            ));
        } else {
            Bukkit.getPluginManager().callEvent(new PaymentEvent(
                    exchange,
                    sender,
                    cash,
                    PaymentEvent.Cause.ORDER_SETTLEMENT
            ));
        }

        // Settle asset
        AssetStack settlement = market.getAsset();
        settlement.setQuantity(settlement.getQuantity() * quantity);

        if (isBuy()) {
            Bukkit.getPluginManager().callEvent(new PaymentEvent(
                    exchange,
                    sender,
                    settlement,
                    PaymentEvent.Cause.ORDER_SETTLEMENT
            ));
        } else {
            Bukkit.getPluginManager().callEvent(new PaymentEvent(
                    sender,
                    exchange,
                    settlement,
                    PaymentEvent.Cause.ORDER_SETTLEMENT
            ));
        }

        // Settle fees
        CashStack brokerageFee = cash.multiply(broker.getBrokerageFeeRate());
        CashStack marketFee = cash.multiply(exchange.getMarketFeeRate());

        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                sender,
                broker,
                brokerageFee,
                PaymentEvent.Cause.BROKERAGE_FEE
        ));

        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                broker,
                exchange,
                marketFee,
                PaymentEvent.Cause.MARKET_FEE
        ));
    }

    @Override
    @JsonIgnore
    public void onCancelled(@NonNull Exchange exchange) {
        // unregister collateral
        // remove order
        // TODO TODO TODO
    }
}
