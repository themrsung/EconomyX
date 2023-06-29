package oasis.economyx.interfaces.trading;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.classes.trading.auction.DutchAuction;
import oasis.economyx.classes.trading.auction.EnglishAuction;
import oasis.economyx.classes.trading.auction.FirstPriceSealedAuction;
import oasis.economyx.classes.trading.auction.SecondPriceSealedAuction;
import oasis.economyx.classes.trading.market.Market;
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

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Market.class, name = "MARKET"),
        @JsonSubTypes.Type(value = DutchAuction.class, name = "DUTCH_AUCTION"),
        @JsonSubTypes.Type(value = EnglishAuction.class, name = "ENGLISH_AUCTION"),
        @JsonSubTypes.Type(value = FirstPriceSealedAuction.class, name = "FIRST_PRICE_AUCTION"),
        @JsonSubTypes.Type(value = SecondPriceSealedAuction.class, name = "SECOND_PRICE_AUCTION"),
})
public interface PriceProvider {
    /**
     * The asset of which price is provided for
     * Contract size is determined by quantity of the unit asset
     * @return Unit asset
     */
    @NonNull
    AssetStack getAsset();

    /**
     * Shortcut getter to contract size
     * @return Contract size
     */
    @NonNegative
    default long getContractSize() {
        return getAsset().getQuantity();
    }

    /**
     * The current fair value of one unit asset
     * @return Price
     */
    @NonNull
    CashStack getPrice();

    /**
     * Shortcut getter for currency
     * @return Currency
     */
    default Cash getCurrency() {
        return getPrice().getAsset();
    }

    /**
     * The cumulative trading volume of this trading day
     * @return Volume
     */
    @NonNegative
    long getVolume();
}
