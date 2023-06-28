package oasis.economyx.classes.trading.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.trading.PriceProviderType;
import oasis.economyx.trading.auction.Bid;
import org.jetbrains.annotations.NotNull;

/**
 * A public auction where the highest bidder is chosen
 * Auction will wait for 1 hour for higher bids
 */
public final class EnglishAuction extends Auction {
    @Override
    @JsonIgnore
    public void processBids(Actor auctioneer) {
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
    public void onDeadlineReached(Actor auctioneer) {
        if (getBids().size() == 0L) {
            setSold(false);
            setPrice(new CashStack(getCurrency(), 0L));
        } else {
            Bid bid = getBids().get(0);
            bid.onSucceeded(auctioneer, bid.getPrice());

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
