package oasis.economyx.classes.trading.market;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.trading.MarketHost;
import oasis.economyx.interfaces.trading.PriceProvider;
import oasis.economyx.interfaces.trading.market.MarketTick;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * An instantiable class of Marketplace
 */
public final class Market implements Marketplace {
    /**
     * Creates a new market
     * @param asset Asset to be traded (quantity denotes contract size)
     * @param currency Quote currency of this market
     */
    public Market(@NonNull AssetStack asset, @NonNull Cash currency) {
        this.asset = asset;
        this.orders = new ArrayList<>();
        this.price = new CashStack(currency, 0L);
        this.volume = 0L;
    }

    public Market() {
        this.asset = null;
        this.orders = new ArrayList<>();
        this.price = null;
        this.volume = 0L;
    }

    public Market(Market other) {
        this.asset = other.asset;
        this.orders = other.orders;
        this.price = other.price;
        this.volume = other.volume;
    }

    @JsonProperty
    @NonNull
    private final AssetStack asset;
    @JsonProperty
    private final List<Order> orders;

    @NonNull
    @JsonProperty
    private CashStack price;

    @NonNegative
    @JsonIgnore
    private transient long volume;

    @NonNull
    @Override
    public AssetStack getAsset() {
        return asset.copy();
    }

    @Override
    @JsonIgnore
    public @NonNull CashStack getPrice() {
        return new CashStack(price);
    }

    @Override
    @JsonIgnore
    public @NonNegative long getVolume() {
        return volume;
    }

    @Override
    @JsonIgnore
    public List<Order> getOrders() {
        return new ArrayList<>(orders);
    }

    @Override
    @JsonIgnore
    public void placeOrder(@NonNull Order order, @NonNull MarketHost exchange) {
        orders.add(order);
        order.onSubmitted(exchange);
    }

    @Override
    @JsonIgnore
    public void cancelOrder(@NonNull Order order, @NonNull MarketHost exchange) {
        if (orders.remove(order)) {
            order.onCancelled(exchange);
        }
    }

    @Override
    @JsonIgnore
    public void processOrders(MarketHost exchange) {
        enforceTickSize(exchange);

        cancelFulfilledOrders(exchange);

        triggerStopLoss();
        triggerStopLimit();

        killImmediateOrders(exchange);

        repriceMarketOrders();

        for (Order bo : getBuyOrders()) {
            for (Order so : getSellOrders()) {
                if (!bo.getPrice().isSmallerThan(so.getPrice())) {
                    CashStack p = bo.getTime().isBefore(so.getTime()) ? bo.getPrice() : so.getPrice();
                    long q = Math.min(bo.getQuantity(), so.getQuantity());

                    if (q >= bo.getQuantity() || bo.allowsPartialFulfillment()) {
                        if (q >= so.getQuantity() || so.allowsPartialFulfillment()) {
                            bo.onFulfilled(exchange, p, q);
                            so.onFulfilled(exchange, p, q);

                            price = p;
                            volume += q;
                        }
                    }
                }
            }
        }
    }

    @JsonIgnore
    private void enforceTickSize(MarketHost exchange) {
        for (Order o : getOrders()) {
            double price = o.getPrice().getQuantity();

            if (price % getTickSize() != 0d) {
                cancelOrder(o, exchange);
            }
        }
    }

    @JsonIgnore
    private void cancelFulfilledOrders(MarketHost exchange) {
        for (Order o : getOrders()) {
            if (o.getQuantity() <= 0L) {
                cancelOrder(o, exchange);
            }
        }
    }

    @JsonIgnore
    private void triggerStopLoss() {
        for (Order o : getOrders()) {
            if (o.getType() == Order.Type.STOP_LOSS_SELL) {
                if (!getPrice().isGreaterThan(o.getPrice())) o.setType(Order.Type.MARKET_SELL);
            } else if (o.getType() == Order.Type.STOP_LOSS_BUY) {
                if (!getPrice().isSmallerThan(o.getPrice())) o.setType(Order.Type.MARKET_BUY);
            }
        }
    }

    @JsonIgnore
    private void triggerStopLimit() {
        for (Order o : getOrders()) {
            if (o.getType() == Order.Type.STOP_LIMIT_SELL) {
                if (!getPrice().isGreaterThan(o.getPrice())) o.setType(Order.Type.LIMIT_SELL);
            } else if (o.getType() == Order.Type.STOP_LIMIT_BUY) {
                if (!getPrice().isSmallerThan(o.getPrice())) o.setType(Order.Type.LIMIT_BUY);
            }
        }
    }

    @JsonIgnore
    private void killImmediateOrders(MarketHost exchange) {
        for (Order o : getOrders()) {
            if (o.isImmediate()) {
                if (o.getTime().plusSeconds(1).isBeforeNow()) {
                    cancelOrder(o, exchange);
                }
            }
        }
    }

    @JsonIgnore
    private void repriceMarketOrders() {
        MarketTick highestAsk = getHighestAsk();
        CashStack askPrice = highestAsk != null ? highestAsk.getPrice() : new CashStack(getCurrency(), Long.MAX_VALUE);

        for (Order o : getBuyOrders()) {
            if (o.isMarket()) o.setPrice(askPrice);
        }

        MarketTick lowestBid = getLowestBid();
        CashStack bidPrice = lowestBid != null ? lowestBid.getPrice() : new CashStack(getCurrency(), 0L);

        for (Order o : getSellOrders()) {
            if (o.isMarket()) o.setPrice(bidPrice);
        }
    }

    @JsonProperty
    private final PriceProvider.Type type = Type.MARKET;

    @JsonIgnore
    public PriceProvider.Type getType() {
        return type;
    }
}
