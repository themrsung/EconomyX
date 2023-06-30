package oasis.economyx.classes.trading.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.trading.AuctionHost;
import oasis.economyx.interfaces.trading.PriceProvider;
import oasis.economyx.interfaces.trading.auction.Bid;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;

/**
 * A public auction where the first bidder is chosen
 */
public final class DutchAuction extends Auction {
    /**
     * Creates a new Dutch auction
     *
     * @param asset        Asset to sell
     * @param deadline     Deadline of auction
     * @param maximumPrice Starting price of this auction (price will fall from maximum price)
     */
    public DutchAuction(@NonNull AssetStack asset, @NonNull DateTime deadline, @NonNull CashStack maximumPrice) {
        super(asset, deadline, maximumPrice);
    }

    public DutchAuction() {
        super();
    }

    public DutchAuction(DutchAuction other) {
        super(other);
    }

    @Override
    @JsonIgnore
    public void processBids(AuctionHost auctioneer) {
        if (getBids().size() == 0L) return;
        onDeadlineReached(auctioneer);
    }

    @Override
    @JsonIgnore
    public void onDeadlineReached(AuctionHost auctioneer) {
        if (getBids().size() == 0L) {
            setSold(false);
            setPrice(new CashStack(getCurrency(), 0L));
        } else {
            Bid bid = getBids().get(0);
            bid.onSucceeded(auctioneer, bid.getPrice());

            // No need to check for reserve price; Reserve price doesn't apply to Dutch auctions

            setSold(true);
            setPrice(bid.getPrice());
        }
    }

    @JsonProperty
    private final Type type = Type.DUTCH_AUCTION;

    @Override
    @JsonIgnore
    public PriceProvider.Type getType() {
        return type;
    }
}
