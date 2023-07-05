package oasis.economyx.types.asset;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.commodity.CommodityStack;
import oasis.economyx.types.asset.contract.collateral.CollateralStack;
import oasis.economyx.types.asset.contract.forward.ForwardStack;
import oasis.economyx.types.asset.contract.note.NoteStack;
import oasis.economyx.types.asset.contract.option.OptionStack;
import oasis.economyx.types.asset.contract.swap.SwapStack;
import oasis.economyx.types.asset.property.PropertyStack;
import oasis.economyx.types.asset.stock.StockStack;
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

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)

@JsonSubTypes({ // Uses Type to designate stack types
        @JsonSubTypes.Type(value = CashStack.class, name = "CASH"),
        @JsonSubTypes.Type(value = StockStack.class, name = "STOCK"),
        @JsonSubTypes.Type(value = CommodityStack.class, name = "COMMODITY"),
        @JsonSubTypes.Type(value = PropertyStack.class, name = "PROPERTY"),
        @JsonSubTypes.Type(value = CollateralStack.class, name = "COLLATERAL"),
        @JsonSubTypes.Type(value = ForwardStack.class, name = "FORWARD"),
        @JsonSubTypes.Type(value = NoteStack.class, name = "NOTE"),
        @JsonSubTypes.Type(value = OptionStack.class, name = "OPTION"),
        @JsonSubTypes.Type(value = SwapStack.class, name = "SWAP"),
})

public interface AssetStack extends References {
    /**
     * The asset being held by this stack
     *
     * @return Asset being held
     */
    @NonNull
    Asset getAsset();

    /**
     * The quantity of this stack
     *
     * @return Quantity
     */
    @NonNegative
    long getQuantity();

    /**
     * Sets the quantity of this stack
     *
     * @param quantity New quantity
     */
    void setQuantity(@NonNegative long quantity);

    /**
     * Adds quantity to this stack
     *
     * @param delta How much to add
     */
    void addQuantity(@NonNegative long delta);

    /**
     * Remvoes quantity from this stack
     *
     * @param delta How much to remove
     * @throws IllegalArgumentException When the resulting quantity is negative
     */
    void removeQuantity(@NonNegative long delta) throws IllegalArgumentException;

    /**
     * Gets the classification ot the asset being held
     *
     * @return Classification
     */
    Asset.Type getType();

    /**
     * The metadata of this asset stack
     * Can be expanded to hold purchase price, contract details, etc.
     *
     * @return Copy of metadata
     */
    @NonNull
    AssetMeta getMeta();

    /**
     * Sets the metadata of this asset stack
     *
     * @param meta New meta of this stack
     * @throws IllegalArgumentException When metadata is incompatible with asset classification
     */
    void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException;

    /**
     * Compares this to another asset stack to check for equality
     * Only checks asset and quantity; Does NOT check metadata
     *
     * @param asset Asset to compare to
     * @return Whether asset stack is the same
     * @throws IllegalArgumentException When asset type if different
     */
    default boolean equals(AssetStack asset) throws IllegalArgumentException {
        if (!getAsset().equals(asset.getAsset())) throw new IllegalArgumentException();

        return getQuantity() == asset.getQuantity();
    }

    /**
     * Copies this asset stack
     *
     * @return An identical copy
     */
    @NonNull
    AssetStack copy();

    /**
     * Formats this asset stack.
     *
     * @return Formatted string
     */
    @NonNull
    String format(@NonNull EconomyState state);

    @Override
    default void initialize(@NonNull EconomyState state) {
        if (getAsset() instanceof References r) r.initialize(state);
    }
}

// NOTE
// This is a personal note; Feel free to ignore.
/*
When creating asset types, always remember that an INSTANCE will be used at runtime.
Try using final primitive types next time to prevent this issue entirely. (Just use a record)
For example, when coding an asset:

Asset {
    final String symbol;
    final long quantity;

    Asset setQuantity(long q) {
        return new Asset(symbol, q);
    }
}

And in AssetStack:

AssetStack {
    Asset asset;
    void setQuantity(long q) {
        asset = asset.setQuantity(q);
    }
}

This will probably be less efficient, but will speed up development dramatically.
This has been bugging me this entire project.
 */
