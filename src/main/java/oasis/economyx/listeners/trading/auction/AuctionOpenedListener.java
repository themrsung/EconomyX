package oasis.economyx.listeners.trading.auction;

import oasis.economyx.EconomyX;
import oasis.economyx.events.trading.auction.AuctionOpenedEvent;
import oasis.economyx.interfaces.actor.types.trading.AuctionHouse;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AuctionOpenedListener extends EconomyListener {
    public AuctionOpenedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAuctionOpened(AuctionOpenedEvent e) {
        if (e.isCancelled()) return;

        AuctionHouse host = e.getHost();
        Auctioneer auction = e.getAuction();

        host.openAuction(auction);
    }
}
