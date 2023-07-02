package oasis.economyx.events.trading.auction;

import oasis.economyx.interfaces.actor.types.trading.AuctionHouse;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AuctionOpenedEvent extends AuctionEvent {
    public AuctionOpenedEvent(@NonNull AuctionHouse host, @NonNull Auctioneer auction) {
        super(host, auction);
    }
}
