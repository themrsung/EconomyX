package oasis.economyx.trading.auction;

import oasis.economyx.actor.Actor;
import oasis.economyx.trading.PriceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;

import java.util.List;

/**
 * An auction provides price by buyers' bids
 * <p>
 *     Both the seller and bidders do not place collateral; Auctions are backed by their parties' credit
 *     Price provided by an auctioneer is less reliable than a marketplace
 *     An ongoing auction's price will be estimated (Method is different for each option type)
 * </p>
 */
public interface Auctioneer extends PriceProvider {
    /**
     * Which type of auction this is
     * @return Auction type
     */
    AuctionType getType();

    /**
     * The expiration of this auction
     * @return When the price is confirmed
     */
    DateTime getDeadline();

    /**
     * Gets all bids placed in this action
     * Sorted by time ascending
     * @return A copied list of bids
     */
    List<Bid> getBids();

    /**
     * Places a bid
     * @param bid Bid to place
     */
    void placeBid(@NonNull Bid bid);

    /**
     * Cancels a bid
     * @param bid Bid to cancel
     */
    void cancelBid(@NonNull Bid bid);

    /**
     * Called every auction tick
     * Processes bids differently depending on auction type
     * @param auctioneer The actor who runs this auction
     */
    void processBids(Actor auctioneer);

    /**
     * Called after final auction tick
     * @param auctioneer The actor who runs this auction
     */
    void onDeadlineReached(Actor auctioneer);
}
