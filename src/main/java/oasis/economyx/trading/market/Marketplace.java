package oasis.economyx.trading.market;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.state.EconomyState;
import oasis.economyx.trading.PriceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A marketplace provides price by orders' price competition
 */
public interface Marketplace extends PriceProvider {
    /**
     * Gets the minimum tick size of this marketplace
     * All orders non-compliant will be cancelled
     */
    @JsonProperty("tickSize")
    double getTickSize();

    /**
     * Gets a copied list of all unfulfilled orders
     */
    @JsonProperty("orders")
    List<Order> getOrders();

    /**
     * Places a new order
     * @param order Order to place
     * @param state Current running state
     */
    @JsonIgnore
    void placeOrder(@NonNull Order order, @NonNull EconomyState state);

    /**
     * Cancels an existing order
     * @param order Order to place
     * @param state Current running state
     */
    @JsonIgnore
    void cancelOrder(@NonNull Order order, @NonNull EconomyState state);

    /**
     * Processes all orders
     * @param state Current running state
     */
    @JsonIgnore
    void processOrders(EconomyState state);

    /**
     * Gets structured buy tick data sorted by price descending
     */
    @JsonIgnore
    default List<MarketTick> getBidTicks() {
        List<MarketTick> ticks = new ArrayList<>();

        for (Order o : getOrders()) {
            if (o.isBuy()) {
                boolean exists = false;

                for (MarketTick t : ticks) {
                    if (t.getPrice() == o.getPrice().getQuantity()) {
                        t.setQuantity(t.getQuantity() + t.getQuantity());
                        exists = true;
                    }
                }

                if (!exists) {
                    ticks.add(new MarketTick(true, o.getPrice().getQuantity(), o.getQuantity()));
                }
            }
        }

        ticks.sort((t1, t2) -> Double.compare(t2.getPrice(), t1.getPrice()));

        return ticks;
    }

    /**
     * Gets lowest bid
     */
    @JsonIgnore
    @Nullable
    default MarketTick getLowestBid() {
        List<MarketTick> bids = getBidTicks();
        return bids.size() > 0 ? bids.get(0) : null;
    }

    /**
     * Gets structured sell tick data sorted by price ascending
     */
    @JsonIgnore
    default List<MarketTick> getAskTicks() {
        List<MarketTick> ticks = new ArrayList<>();

        for (Order o : getOrders()) {
            if (!o.isBuy()) {
                boolean exists = false;

                for (MarketTick t : ticks) {
                    if (t.getPrice() == o.getPrice().getQuantity()) {
                        t.setQuantity(t.getQuantity() + t.getQuantity());
                        exists = true;
                    }
                }

                if (!exists) {
                    ticks.add(new MarketTick(false, o.getPrice().getQuantity(), o.getQuantity()));
                }
            }
        }

        ticks.sort((t1, t2) -> Double.compare(t1.getPrice(), t2.getPrice()));

        return ticks;
    }

    /**
     * Gets highest ask
     */
    @JsonIgnore
    @Nullable
    default MarketTick getHighestAsk() {
        List<MarketTick> asks = getAskTicks();
        return asks.size() > 0 ? asks.get(0) : null;
    }

}
