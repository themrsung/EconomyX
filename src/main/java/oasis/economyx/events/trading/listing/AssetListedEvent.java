package oasis.economyx.events.trading.listing;

import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents the listing of an asset to an exchange
 */
public final class AssetListedEvent extends ListingEvent {
    /**
     * Creates a new asset listed event
     * @param exchange ExchangeCompany to list this asset
     * @param listing Asset to list to exchange
     */
    public AssetListedEvent(@NonNull Exchange exchange, @NonNull Marketplace listing) {
        super(exchange, listing);
    }
}
