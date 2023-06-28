package oasis.economyx.classes.trading.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.trading.auction.AuctionType;
import oasis.economyx.trading.auction.Auctioneer;
import oasis.economyx.trading.auction.Bid;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * An instantiable class of Auctioneer
 */
public abstract class Auction implements Auctioneer {
    public Auction() {
        this.asset = null;
        this.type = null;
        this.deadline = null;
        this.bids = new ArrayList<>();
    }

    public Auction(Auction other) {
        this.asset = other.asset;
        this.type = other.type;
        this.deadline = other.deadline;
        this.bids = other.bids;
    }

    @JsonProperty
    private final AssetStack asset;
    @JsonProperty
    private final AuctionType type;
    @JsonProperty
    private final DateTime deadline;
    @JsonProperty
    private final List<Bid> bids;

    @Override
    @JsonIgnore
    public AuctionType getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public DateTime getDeadline() {
        return deadline;
    }

    @Override
    @JsonIgnore
    public List<Bid> getBids() {
        List<Bid> bids = new ArrayList<>(this.bids);
        bids.sort((b1, b2) -> b1.getTime().compareTo(b2.getTime()));
        return bids;
    }

    @Override
    @JsonIgnore
    public void placeBid(@NonNull Bid bid) {
        bids.add(bid);
    }

    @Override
    @JsonIgnore
    public void cancelBid(@NonNull Bid bid) {
        bids.remove(bid);
    }

    @Override
    @JsonIgnore
    public void processBids(Actor auctioneer) {
        // Dutch auctions are closed immediately after the first bid
        if (getType() == AuctionType.DUTCH) {
            if (getBids().size() > 0L) {
                onDeadlineReached(auctioneer);
                return;
            }
        }


    }

    @Override
    @JsonIgnore
    public void onDeadlineReached(Actor auctioneer) {
        if (getBids().size() == 0L) return;
        else if (getBids().size() == 1L) {
            // Every auction behaves the same when only one bid is placed
            Bid bid = getBids().get(0);
            CashStack price = bid.getPrice();
            bid.onSucceeded(auctioneer, price);
        } else {
            // TODO
        }
    }
}
