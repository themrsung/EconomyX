package oasis.economyx.asset;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.asset.commodity.CommodityStack;
import oasis.economyx.asset.stock.StockStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An asset stack represents the holding of one asset
 * Quantity cannot be negative
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = CashStack.class, name = "cash"),
        @JsonSubTypes.Type(value = StockStack.class, name = "stock"),
        @JsonSubTypes.Type(value = CommodityStack.class, name = "commodity"),
})

public interface AssetStack {
    /**
     * The asset being held by this stack
     * @return Asset being held
     */
    @NonNull
    @JsonProperty("asset")
    Asset getAsset();

    /**
     * The quantity of this stack
     * @return Quantity
     */
    @NonNegative
    @JsonProperty("quantity")
    long getQuantity();

    /**
     * Sets the quantity of this stack
     * @param quantity New quantity
     */
    @JsonIgnore
    void setQuantity(@NonNegative long quantity);

    /**
     * Adds quantity to this stack
     * @param delta How much to add
     */
    @JsonIgnore
    void addQuantity(@NonNegative long delta);

    /**
     * Remvoes quantity from this stack
     * @param delta How much to remove
     * @throws IllegalArgumentException When the resulting quantity is negative
     */
    @JsonIgnore
    void removeQuantity(@NonNegative long delta) throws IllegalArgumentException;

    /**
     * A shortcut getter to asset classification
     * @return Classification of the asset being held
     */
    @JsonIgnore
    default AssetType getType() {
        return getAsset().getType();
    }

    /**
     * The metadata of this asset stack
     * Can be expanded to hold purchase price, contract details, etc.
     * @return Copy of metadata
     */
    @NonNull
    @JsonProperty("meta")
    AssetMeta getMeta();

    /**
     * Sets the metadata of this asset stack
     * @param meta New meta of this stack
     * @throws IllegalArgumentException When metadata is incompatible with asset classification
     */
    @JsonIgnore
    void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException;
}
