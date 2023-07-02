package oasis.economyx.events.trading.auction;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.trading.AuctionHouse;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class AuctionEvent extends EconomyEvent {
    public AuctionEvent(@NonNull AuctionHouse host, @NonNull Auctioneer auction) {
        this.host = host;
        this.auction = auction;
    }

    @NonNull
    private final AuctionHouse host;

    @NonNull
    private final Auctioneer auction;

    @NonNull
    public AuctionHouse getHost() {
        return host;
    }

    @NonNull
    public Auctioneer getAuction() {
        return auction;
    }
}
