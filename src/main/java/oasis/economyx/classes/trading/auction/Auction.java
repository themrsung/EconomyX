package oasis.economyx.classes.trading.auction;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.interfaces.trading.auction.Bid;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;

import java.util.ArrayList;
import java.util.List;

/**
 * An instantiable class of Auctioneer
 */
public abstract class Auction implements Auctioneer {
    public Auction() {
        this.asset = null;
        this.denotation = null;
        this.deadline = null;
        this.bids = new ArrayList<>();
        this.price = null;
        this.sold = false;
    }

    public Auction(Auction other) {
        this.asset = other.asset;
        this.denotation = other.denotation;
        this.deadline = other.deadline;
        this.bids = other.bids;
        this.price = other.price;
        this.sold = other.sold;
    }

    @JsonProperty
    @NonNull
    private final AssetStack asset;

    @JsonProperty
    @NonNull
    private final Cash denotation;

    @JsonProperty
    @NonNull
    private final DateTime deadline;

    @JsonProperty
    @NonNull
    private final List<Bid> bids;

    @JsonProperty
    @NotNull
    private CashStack price;

    @JsonProperty
    private boolean sold;

    @Override
    @JsonIgnore
    public boolean isSold() {
        return sold;
    }

    @JsonIgnore
    protected void setSold(boolean sold) {
        this.sold = sold;
    }

    @NotNull
    @Override
    public AssetStack getAsset() {
        return asset.copy();
    }
    @Override
    @JsonIgnore
    public @NotNull DateTime getDeadline() {
        return deadline;
    }

    @NotNull
    @Override
    @JsonIgnore
    public CashStack getPrice() {
        return new CashStack(price);
    }

    @JsonIgnore
    protected void setPrice(@NonNull CashStack price) {
        this.price = price;
    }

    @Override
    @JsonIgnore
    public @NotNull List<Bid> getBids() {
        List<Bid> bids = new ArrayList<>(this.bids);
        bids.sort((b1, b2) -> b2.getPrice().compare(b1.getPrice()));
        return bids;
    }

    @Override
    @JsonIgnore
    public void placeBid(@NonNull Bid bid) {
        bids.add(bid);
    }
}
