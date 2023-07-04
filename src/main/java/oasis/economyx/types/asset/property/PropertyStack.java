package oasis.economyx.types.asset.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * A property (cannot be plural)
 */
public final class PropertyStack implements AssetStack {
    public PropertyStack(@NonNull Property asset) {
        this.asset = asset;
        this.meta = new PropertyMeta();
        this.quantity = 1L;
    }

    public PropertyStack(@NonNull Property asset, @NonNull PropertyMeta meta) {
        this.asset = asset;
        this.meta = meta;
        this.quantity = 1L;
    }

    public PropertyStack(PropertyStack other) {
        this.asset = other.asset;
        this.meta = other.meta;
        this.quantity = other.quantity;
    }

    @NonNull
    @JsonProperty
    private final Property asset;

    @NonNegative
    @JsonProperty
    private long quantity;

    @NonNull
    @JsonProperty
    private PropertyMeta meta;

    @NonNull
    @Override
    @JsonIgnore
    public Property getAsset() {
        return new Property(asset);
    }

    @Override
    @JsonIgnore
    public long getQuantity() {
        return quantity;
    }

    /**
     * Sets the quantity of this stack.
     * @param quantity New quantity (either 1 or 0)
     * @throws IllegalArgumentException When given quantity is not within bounds (0 <= q <= 1)
     */
    @Override
    @JsonIgnore
    public void setQuantity(long quantity) throws IllegalArgumentException {
        if (!(quantity == 1L || quantity == 0L)) throw new IllegalArgumentException();
        this.quantity = quantity;
    }

    @Override
    @JsonIgnore
    public void addQuantity(@NonNegative long delta) throws IllegalArgumentException {
        setQuantity(getQuantity() + delta);
    }

    @Override
    @JsonIgnore
    public void removeQuantity(@NonNegative long delta) throws IllegalArgumentException {
        setQuantity(getQuantity() - delta);
    }

    @NonNull
    @Override
    @JsonIgnore
    public PropertyMeta getMeta() {
        return new PropertyMeta(meta);
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof PropertyMeta)) throw new IllegalArgumentException();

        this.meta = (PropertyMeta) meta;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.PROPERTY;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull PropertyStack copy() {
        return new PropertyStack(this);
    }

    @Override
    public @NonNull String format(@NonNull EconomyState state) {
        return "부동산: " + getAsset().getArea().format();
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        AssetStack.super.initialize(state);

        meta.initialize(state);
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    private PropertyStack() {
        this.asset = new Property();
        this.meta = new PropertyMeta();
    }
}
