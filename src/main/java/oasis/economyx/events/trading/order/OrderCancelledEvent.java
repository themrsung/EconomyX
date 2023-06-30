package oasis.economyx.events.trading.order;

import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.interfaces.trading.market.Order;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents the cancellation of an order.
 */
public final class OrderCancelledEvent extends OrderEvent {
    public OrderCancelledEvent(@NonNull Exchange exchange, @NonNull Marketplace listing, @NonNull Order order) {
        super(exchange, listing, order);
    }
}
