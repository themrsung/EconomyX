package oasis.economyx.listeners.trading.bid;

import oasis.economyx.EconomyX;
import oasis.economyx.events.trading.bid.BidPlacedEvent;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.interfaces.trading.auction.Bid;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BidPlacedListener extends EconomyListener {
    public BidPlacedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBidPlaced(BidPlacedEvent e) {
        if (e.isCancelled()) return;

        Auctioneer auction = e.getAuction();
        Bid bid = e.getBid();

        auction.placeBid(bid);
    }
}
