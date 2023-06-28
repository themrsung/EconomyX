package oasis.economyx.trading.market;

import oasis.economyx.actor.Actor;
import oasis.economyx.trading.PriceProvider;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A marketplace provides price by orders' price competition
 */

public interface Marketplace extends PriceProvider {
    /**
     * Gets all unfulfilled orders
     * @return A copied list or orders
     */
    List<Order> getOrders();

    /**
     * Gets all buy orders
     * @return List of buy orders
     */
    default List<Order> getBuyOrders() {
        List<Order> buyOrders = new ArrayList<>();

        for (Order o : getOrders()) {
            if (o.isBuy()) buyOrders.add(o);
        }

        return buyOrders;
    }

    /**
     * Gets all sell orders
     * @return List of sell orders
     */
    default List<Order> getSellOrders() {
        List<Order> sellOrders = new ArrayList<>();

        for (Order o : getOrders()) {
            if (!o.isBuy()) sellOrders.add(o);
        }

        return sellOrders;
    }

    /**
     * Places a new order
     * @param order Order to place
     * @param exchange The actor running this market
     */
    void placeOrder(@NonNull Order order, @NonNull Actor exchange);

    /**
     * Cancels an existing order
     * @param order Order to place
     * @param exchange The actor running this market
     */
    void cancelOrder(@NonNull Order order, @NonNull Actor exchange);

    /**
     * Processes orders
     * Called every market tick
     * @param exchange The actor running this market
     */
    void processOrders(Actor exchange);

    /**
     * Gets structured buy tick data sorted by price descending
     */
    default List<MarketTick> getBidTicks() {
        List<MarketTick> ticks = new ArrayList<>();

        for (Order o : getOrders()) {
            if (o.isBuy()) {
                boolean exists = false;

                for (MarketTick t : ticks) {
                    if (t.getPrice().equals(o.getPrice())) {
                        t.setQuantity(t.getQuantity() + t.getQuantity());
                        exists = true;
                    }
                }

                if (!exists) {
                    ticks.add(new MarketTick(true, o.getPrice(), o.getQuantity()));
                }
            }
        }

        ticks.sort((t1, t2) -> t2.getPrice().compare(t1.getPrice()));

        return ticks;
    }

    /**
     * Gets lowest bid
     */
    @Nullable
    default MarketTick getLowestBid() {
        List<MarketTick> bids = getBidTicks();
        return bids.size() > 0 ? bids.get(0) : null;
    }

    /**
     * Gets structured sell tick data sorted by price ascending
     */
    default List<MarketTick> getAskTicks() {
        List<MarketTick> ticks = new ArrayList<>();

        for (Order o : getOrders()) {
            if (!o.isBuy()) {
                boolean exists = false;

                for (MarketTick t : ticks) {
                    if (t.getPrice().equals(o.getPrice())) {
                        t.setQuantity(t.getQuantity() + t.getQuantity());
                        exists = true;
                    }
                }

                if (!exists) {
                    ticks.add(new MarketTick(false, o.getPrice(), o.getQuantity()));
                }
            }
        }

        ticks.sort((t1, t2) -> t1.getPrice().compare(t2.getPrice()));

        return ticks;
    }

    /**
     * Gets highest ask
     */
    @Nullable
    default MarketTick getHighestAsk() {
        List<MarketTick> asks = getAskTicks();
        return asks.size() > 0 ? asks.get(0) : null;
    }

    /**
     * Gets the minimum tick size of this marketplace
     * All orders non-compliant will be cancelled
     */
    @NonNegative
    default double getTickSize() {
        return getAsset().getQuantity();
    }
}
