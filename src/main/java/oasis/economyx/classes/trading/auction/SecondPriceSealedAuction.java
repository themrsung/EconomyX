package oasis.economyx.classes.trading.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.trading.PriceProviderType;
import oasis.economyx.trading.auction.Bid;
import org.jetbrains.annotations.NotNull;

/**
 * A sealed auction where the highest bidder pays the price os the second-highest bidder
 * Auction will not close if only one bid is placed
 */
public final class SecondPriceSealedAuction extends Auction {

    @Override
    @JsonIgnore
    public void processBids(Actor auctioneer) {
        // Do nothing
    }

    @Override
    @JsonIgnore
    public void onDeadlineReached(Actor auctioneer) {
        if (getBids().size() <= 1L) {
            setSold(false);
            setPrice(new CashStack(getCurrency(), 0L));
        } else {
            Bid first = getBids().get(0);
            Bid second = getBids().get(1);

            first.onSucceeded(auctioneer, second.getPrice());

            setSold(true);
            setPrice(second.getPrice());
        }
    }

    @JsonProperty
    private final PriceProviderType type = PriceProviderType.SECOND_PRICE_AUCTION;

    @NotNull
    @Override
    @JsonIgnore
    public PriceProviderType getType() {
        return type;
    }
}
