package oasis.economyx.interfaces.trading;

import com.fasterxml.jackson.annotation.*;
import oasis.economyx.classes.trading.auction.DutchAuction;
import oasis.economyx.classes.trading.auction.EnglishAuction;
import oasis.economyx.classes.trading.auction.FirstPriceSealedAuction;
import oasis.economyx.classes.trading.auction.SecondPriceSealedAuction;
import oasis.economyx.classes.trading.market.Market;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Provides fair value of an asset
 * Price is used for exercising options and settling swaps
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Market.class, name = "MARKET"),
        @JsonSubTypes.Type(value = DutchAuction.class, name = "DUTCH_AUCTION"),
        @JsonSubTypes.Type(value = EnglishAuction.class, name = "ENGLISH_AUCTION"),
        @JsonSubTypes.Type(value = FirstPriceSealedAuction.class, name = "FIRST_PRICE_AUCTION"),
        @JsonSubTypes.Type(value = SecondPriceSealedAuction.class, name = "SECOND_PRICE_AUCTION"),
})
public interface PriceProvider extends References {
    /**
     * Gets the unique ID of this price provider.
     *
     * @return Unique ID
     */
    UUID getUniqueId();

    /**
     * The asset of which price is provided for
     * Contract size is determined by quantity of the unit asset
     *
     * @return Unit asset
     */
    @NonNull
    @JsonIgnore
    AssetStack getAsset();

    /**
     * Shortcut getter to contract size
     *
     * @return Contract size
     */
    @NonNegative
    @JsonIgnore
    default long getContractSize() {
        return getAsset().getQuantity();
    }

    /**
     * The current fair value of one unit asset
     *
     * @return Price
     */
    @NonNull
    @JsonIgnore
    CashStack getPrice();

    /**
     * Shortcut getter for currency
     *
     * @return Currency
     */
    @JsonIgnore
    default Cash getCurrency() {
        return getPrice().getAsset();
    }

    /**
     * The cumulative trading volume of this trading day
     *
     * @return Volume
     */
    @NonNegative
    @JsonIgnore
    long getVolume();

    enum Type {
        // Markets

        /**
         * A market where orders compete to form a fair price
         */
        MARKET,

        // Auctions

        /**
         * A public auction where the highest bidder is chosen
         */
        ENGLISH_AUCTION,

        /**
         * A public auction where the first bidder is chosen
         */
        DUTCH_AUCTION,

        /**
         * A sealed auction where the highest bidder is chosen
         */
        FIRST_PRICE_AUCTION,

        /**
         * A sealed auction where the highest bidder pays the price os the second-highest bidder
         */
        SECOND_PRICE_AUCTION
    }

    @Override
    default void initialize(@NonNull EconomyState state) {
        getAsset().initialize(state);
    }
}
