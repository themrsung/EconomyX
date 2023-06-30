package oasis.economyx.classes.actor.company.trading;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.trading.AuctionHouse;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class AuctionCompany extends Company implements AuctionHouse {
    /**
     * Creates a new auction house
     *
     * @param uniqueId   Unique ID of this auction house
     * @param name       Name of this auction house (not unique)
     * @param stockId    ID of this auction house's stock
     * @param shareCount Initial share count
     * @param currency   Currency to host auctions in
     */
    public AuctionCompany(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.auctions = new ArrayList<>();
        this.auctionFeeRate = 0f;
    }

    public AuctionCompany() {
        this.auctions = new ArrayList<>();
        this.auctionFeeRate = 0f;
    }

    public AuctionCompany(AuctionCompany other) {
        super(other);
        this.auctions = other.auctions;
        this.auctionFeeRate = other.auctionFeeRate;
    }

    @JsonProperty
    @NonNull
    private final List<Auctioneer> auctions;

    @JsonProperty
    @NonNegative
    private float auctionFeeRate;

    @NonNull
    @Override
    @JsonIgnore
    public List<Auctioneer> getAuctions() {
        return new ArrayList<>(auctions);
    }

    @Override
    @JsonIgnore
    public void openAuction(@NonNull Auctioneer auction) {
        this.auctions.add(auction);
    }

    @Override
    @JsonIgnore
    public float getAuctionFeeRate() {
        return auctionFeeRate;
    }

    @Override
    @JsonIgnore
    public void setAuctionFeeRate(float auctionFeeRate) {
        this.auctionFeeRate = auctionFeeRate;
    }

    @JsonProperty
    private final Type type = Type.AUCTION_COMPANY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
