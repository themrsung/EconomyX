package oasis.economyx.listeners.trading.order;

import oasis.economyx.EconomyX;
import oasis.economyx.events.trading.order.OrderPlacedEvent;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class OrderPlacedListener extends EconomyListener {
    public OrderPlacedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOrderPlaced(OrderPlacedEvent e) {
        if (e.isCancelled()) return;

        Marketplace listing = e.getListing();
        listing.placeOrder(e.getOrder(), e.getExchange());
    }
}
