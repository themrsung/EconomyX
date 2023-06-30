package oasis.economyx.events.trading.listing;

import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents the delisting of an asset from an exchange
 */
public final class AssetDelistedEvent extends ListingEvent {
    /**
     * Creates a new asset delisted event.
     * @param exchange ExchangeCompany to delist the asset from
     * @param listing Listing to delist
     */
    public AssetDelistedEvent(@NonNull Exchange exchange, @NonNull Marketplace listing) {
        super(exchange, listing);
    }
}
