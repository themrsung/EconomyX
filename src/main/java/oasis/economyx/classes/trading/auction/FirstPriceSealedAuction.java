package oasis.economyx.classes.trading.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.trading.AuctionHost;
import oasis.economyx.interfaces.trading.PriceProvider;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.interfaces.trading.auction.Bid;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

/**
 * A sealed auction where the highest bidder is chosen
 */
public final class FirstPriceSealedAuction extends Auction {
    /**
     * Creates a new first-price sealed auction
     * @param asset Asset to sell
     * @param deadline Deadline of this auction
     * @param reservePrice Reserve price of this auction (Asset will sell for at least reserve price)
     */
    public FirstPriceSealedAuction(@NonNull AssetStack asset, @NonNull DateTime deadline, @NonNull CashStack reservePrice) {
        super(asset, deadline, reservePrice);
    }

    public FirstPriceSealedAuction() {
        super();
    }

    public FirstPriceSealedAuction(FirstPriceSealedAuction other) {
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
        if (getBids().size() == 0L) {
            setSold(false);
            setPrice(new CashStack(getCurrency(), 0L));
        } else {
            Bid bid = getBids().get(0);
            bid.onSucceeded(auctioneer, bid.getPrice());

            // Check if price is above reserve
            if (bid.getPrice().isSmallerThan(getPrice())) {
                setSold(false);
                setPrice(new CashStack(getCurrency(), 0L));
                return;
            }

            setSold(true);
            setPrice(bid.getPrice());
        }
    }

    @JsonProperty
    private final Type type = Type.FIRST_PRICE_AUCTION;

    @NotNull
    @Override
    @JsonIgnore
    public PriceProvider.Type getType() {
        return type;
    }
}
