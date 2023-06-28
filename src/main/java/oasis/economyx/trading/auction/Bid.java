package oasis.economyx.trading.auction;

import oasis.economyx.actor.Actor;
import oasis.economyx.asset.cash.CashStack;
import org.joda.time.DateTime;

public interface Bid {
    /**
     * The bidder of this bid
     * @return Bidder
     */
    Actor getBidder();

    /**
     * When this bid was placed
     * @return Time of bid
     */
    DateTime getTime();

    /**
     * The price of this bid
     * Price of auction bids are not editable
     * @return Price
     */
    CashStack getPrice();
}
