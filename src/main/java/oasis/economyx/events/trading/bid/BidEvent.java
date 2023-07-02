package oasis.economyx.events.trading.bid;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.interfaces.trading.auction.Bid;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class BidEvent extends EconomyEvent {
    public BidEvent(@NonNull Auctioneer auction, @NonNull Bid bid) {
        this.auction = auction;
        this.bid = bid;
    }

    @NonNull
    private final Auctioneer auction;

    @NonNull
    private final Bid bid;

    @NonNull
    public Auctioneer getAuction() {
        return auction;
    }

    @NonNull
    public Bid getBid() {
        return bid;
    }
}
