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
 * A sealed auction where the highest bidder pays the price os the second-highest bidder
 * Auction will not close if only one bid is placed
 */
public final class SecondPriceSealedAuction extends Auction {
    /**
     * Creates a new second-price sealed auction
     * @param asset Asset to sell
     * @param deadline Deadline of this auction
     * @param reservePrice Reserve price of this auction (asset will sell for at least the reserve price)
     */
    public SecondPriceSealedAuction(@NonNull AssetStack asset, @NonNull DateTime deadline, @NonNull CashStack reservePrice) {
        super(asset, deadline, reservePrice);
    }

    public SecondPriceSealedAuction() {
        super();
    }

    public SecondPriceSealedAuction(SecondPriceSealedAuction other) {
        super(other);
    }

    @Override
    @JsonIgnore
    public void processBids(AuctionHost auctioneer) {
        // Do nothing
    }

    @Override
    @JsonIgnore
    public void onDeadlineReached(AuctionHost auctioneer) {
        if (getBids().size() <= 1L) {
            setSold(false);
            setPrice(new CashStack(getCurrency(), 0L));
        } else {
            Bid first = getBids().get(0);
            Bid second = getBids().get(1);

            // Check if price is above reserve
            if (second.getPrice().isSmallerThan(getPrice())) {
                setSold(false);
                setPrice(new CashStack(getCurrency(), 0L));
                return;
            }

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
