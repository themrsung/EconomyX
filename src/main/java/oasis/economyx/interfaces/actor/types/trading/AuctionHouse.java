package oasis.economyx.interfaces.actor.types.trading;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * Able to host auctions and collect fees from buyer
 * An auction host must buy the asset at the reserve price if there is no bidder
 * Note that auctions cannot be closed prematurely
 */
public interface AuctionHouse extends Actor {
    /**
     * Gets all open auctions
     *
     * @return A copied list of auctions
     */
    @NonNull
    @JsonIgnore
    List<Auctioneer> getAuctions();

    /**
     * Opens an auction
     *
     * @param auction Auction to open
     */
    @JsonIgnore
    void openAuction(@NonNull Auctioneer auction);

    /**
     * Gets the fee rate of auctions
     *
     * @return Rate (e.g. 2% -> 0.02f)
     */
    @JsonIgnore
    float getAuctionFeeRate();

    /**
     * Sets the fee rate of auctions
     *
     * @param rate Rate (e.g. 2% -> 0.02f)
     */
    @JsonIgnore
    void setAuctionFeeRate(float rate);
}
