package oasis.economyx.trading.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.state.EconomyState;
import oasis.economyx.trading.PriceProvider;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;

import java.util.List;

/**
 * An auction provides price by buyers' bids
 * Price provided by an auctioneer is less reliable than a marketplace
 * An ongoing auction's price will be estimated (Method is different for each option type)
 */
public interface Auction extends PriceProvider {
    /**
     * Which type of auction this is
     * @return Auction type
     */
    @JsonProperty("auctionType")
    AuctionType getAuctionType();

    /**
     * The expiration of this auction
     * @return When the price is confirmed
     */
    @JsonProperty("bidDeadline")
    DateTime getBidDeadline();

    /**
     * Gets all bids placed in this action
     * @return A copied list of bids
     */
    @JsonProperty("bids")
    List<Bid> getBids();

    /**
     * Places a bid
     * @param bid Bid to place
     */
    @JsonIgnore
    void placeBid(@NonNull Bid bid);

    /**
     * Called every auction tick
     * Processes bids differently depending on auction type
     * @param state Current running state
     */
    @JsonIgnore
    void processBids(EconomyState state);
}
