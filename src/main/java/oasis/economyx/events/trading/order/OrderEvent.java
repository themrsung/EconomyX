package oasis.economyx.events.trading.order;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A base class for order placement/cancellation events.
 */
public abstract class OrderEvent extends EconomyEvent {
    public OrderEvent(@NonNull Exchange exchange, @NonNull Marketplace listing, @NonNull Order order) {
        this.exchange = exchange;
        this.listing = listing;
        this.order = order;
    }

    @NonNull
    private final Exchange exchange;

    @NonNull
    private final Marketplace listing;

    @NonNull
    private final Order order;

    @NonNull
    public Exchange getExchange() {
        return exchange;
    }

    @NonNull
    public Marketplace getListing() {
        return listing;
    }

    @NonNull
    public Order getOrder() {
        return order;
    }
}
