package oasis.economyx.interfaces.trading.auction;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.trading.AuctionHost;
import oasis.economyx.interfaces.trading.PriceProvider;
import oasis.economyx.interfaces.trading.PriceProviderType;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;

import java.util.List;

/**
 * An auction provides price by buyers' bids
 * <p>
 *     Both the seller and bidders do not place collateral; Auctions are backed by their parties' credit
 *     Price provided by an auctioneer is less reliable than a marketplace
 *     An ongoing auction's price will return the reserve price (starting price for Dutch auctions)
 * </p>
 */
public interface Auctioneer extends PriceProvider {
    /**
     * Which type of auction this is
     * @return Auction type
     */
    @NonNull
    PriceProviderType getType();

    /**
     * The expiration of this auction
     * @return When the price is confirmed
     */
    @NonNull
    DateTime getDeadline();

    /**
     * Whether this auction has expired
     * @return True if auction has closed
     */
    default boolean hasExpired() {
        return getDeadline().isBeforeNow();
    }

    /**
     * Gets all bids placed in this action
     * Sorted by price descending
     * @return A copied list of bids
     */
    @NonNull
    List<Bid> getBids();

    /**
     * Places a bid
     * @param bid Bid to place
     */
    void placeBid(@NonNull Bid bid);

    /**
     * Called every auction tick
     * Processes bids differently depending on auction type
     * @param auctioneer The actor who runs this auction
     */
    void processBids(AuctionHost auctioneer);

    /**
     * Called after final auction tick
     * @param auctioneer The actor who runs this auction
     */
    void onDeadlineReached(AuctionHost auctioneer);

    /**
     * Whether this auction was successful
     * @return True if at least one bid was successful
     */
    boolean isSold();

    /**
     * An auction's volume is either 0 or the amount of asset traded
     * @return Volume
     */
    @Override
    @NonNegative
    default long getVolume() {
        return isSold() ? getAsset().getQuantity() : 0L;
    }
}
