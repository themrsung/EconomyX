package oasis.economyx.classes.trading.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.trading.PriceProviderType;
import oasis.economyx.trading.auction.Bid;
import org.jetbrains.annotations.NotNull;

/**
 * A sealed auction where the highest bidder is chosen
 */
public final class FirstPriceSealedAuction extends Auction {

    @Override
    @JsonIgnore
    public void processBids(Actor auctioneer) {
        // Do nothing
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
    private final PriceProviderType type = PriceProviderType.FIRST_PRICE_AUCTION;

    @NotNull
    @Override
    @JsonIgnore
    public PriceProviderType getType() {
        return type;
    }
}
