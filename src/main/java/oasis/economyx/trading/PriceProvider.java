package oasis.economyx.trading;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.trading.auction.Auction;
import oasis.economyx.trading.market.Marketplace;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Provides fair value of an asset
 * Price is used for exercising options and settling swaps
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Marketplace.class, name = "market"),
        @JsonSubTypes.Type(value = Auction.class, name = "auction")
})

public interface PriceProvider {
    /**
     * The asset of which price is provided for
     * Contract size is determined by quantity of the unit asset
     * @return Unit asset
     */
    @NonNull
    @JsonProperty("asset")
    AssetStack getAsset();

    /**
     * Shortcut getter to contract size
     * @return Contract size
     */
    @NonNegative
    @JsonIgnore
    default long getContractSize() {
        return getAsset().getQuantity();
    }

    /**
     * The current fair value of one unit asset
     * @return Price
     */
    @NonNull
    @JsonProperty("price")
    CashStack getPrice();

    /**
     * The cumulative trading volume of this trading day
     * @return Volume
     */
    @NonNegative
    @JsonIgnore
    long getVolume();
}
