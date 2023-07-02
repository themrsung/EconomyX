package oasis.economyx.events.trading.bid;

import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.interfaces.trading.auction.Bid;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BidPlacedEvent extends BidEvent {
    public BidPlacedEvent(@NonNull Auctioneer auction, @NonNull Bid bid) {
        super(auction, bid);
    }
}
