package oasis.economyx.interfaces.trading.auction;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.cash.CashStack;
import org.joda.time.DateTime;

/**
 * Bids are placed in auctions
 * Unlike orders, there is no collateral backing the bid
 * A bid is not cancellable
 */
public interface Bid {
    /**
     * The bidder of this bid
     *
     * @return Bidder
     */
    Actor getBidder();

    /**
     * When this bid was placed
     *
     * @return Time of bid
     */
    DateTime getTime();

    /**
     * The price of this bid
     * Price of auction bids are not editable
     *
     * @return Price
     */
    CashStack getPrice();

    /**
     * Called when the bid is successful
     *
     * @param auctioneer The actor who runs the auction
     * @param price      Actual price the bidder must pay
     */
    void onSucceeded(Actor auctioneer, CashStack price);
}
