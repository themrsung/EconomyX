package oasis.economyx.listeners.trading.listing;

import oasis.economyx.EconomyX;
import oasis.economyx.events.trading.listing.AssetDelistedEvent;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AssetDelistedListener extends EconomyListener {
    public AssetDelistedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAssetDelisted(AssetDelistedEvent e) {
        if (e.isCancelled()) return;

        Exchange exchange = e.getExchange();
        exchange.delistMarket(e.getListing());
    }
}
