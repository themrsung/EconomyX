package oasis.economyx.events.trading.listing;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A base class for listing/delisting
 */
public abstract class ListingEvent extends EconomyEvent {
    public ListingEvent(@NonNull Exchange exchange, @NonNull Marketplace listing) {
        this.exchange = exchange;
        this.listing = listing;
    }

    @NonNull
    private final Exchange exchange;

    @NonNull
    private final Marketplace listing;

    @NonNull
    public Exchange getExchange() {
        return exchange;
    }

    @NonNull
    public Marketplace getListing() {
        return listing;
    }

}
