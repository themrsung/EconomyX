package oasis.economyx.classes.trading.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.trading.AuctionHost;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.interfaces.trading.PriceProviderType;
import oasis.economyx.interfaces.trading.auction.Bid;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

/**
 * A public auction where the highest bidder is chosen
 * Auction will wait for 1 hour for higher bids
 */
public final class EnglishAuction extends Auction {
    /**
     * Creates a new English auction
     * @param asset Asset to sell
     * @param deadline Deadline of this auction
     * @param reservePrice Starting price of this auction (price will rise from reserve price)
     */
    public EnglishAuction(@NonNull AssetStack asset, @NonNull DateTime deadline, @NonNull CashStack reservePrice) {
        super(asset, deadline, reservePrice);
    }

    public EnglishAuction() {
        super();
    }

    public EnglishAuction(EnglishAuction other) {
        super(other);
    }

    @Override
    @JsonIgnore
    public void processBids(AuctionHost auctioneer) {
        if (getBids().size() == 0L) return;
        else {
            // Highest bid is always the most recent bid in an English auction
            if (getBids().get(0).getTime().plusHours(1).isBeforeNow()) {
                // Consider the highest bid successful
                onDeadlineReached(auctioneer);
            }
        }
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

            // No need to check for reserve price; Bids are always higher than reserve

            setSold(true);
            setPrice(bid.getPrice());
        }
    }

    @JsonProperty
    private final PriceProviderType type = PriceProviderType.ENGLISH_AUCTION;

    @NotNull
    @Override
    @JsonIgnore
    public PriceProviderType getType() {
        return type;
    }
}
