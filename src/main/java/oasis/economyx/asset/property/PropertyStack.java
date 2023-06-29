package oasis.economyx.asset.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.AssetType;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

/**
 * A property (cannot be plural)
 */
public final class PropertyStack implements AssetStack {
    public PropertyStack(@NotNull Property asset) {
        this.asset = asset;
        this.meta = new PropertyMeta();
    }

    public PropertyStack(@NotNull Property asset,@NotNull PropertyMeta meta) {
        this.asset = asset;
        this.meta = meta;
    }

    public PropertyStack(PropertyStack other) {
        this.asset = other.asset;
        this.meta = other.meta;
    }

    @NonNull
    @JsonProperty
    private final Property asset;

    // TODO add location

    /**
     * Properties cannot be plural
     * This is required to be a constant variable due to IO
     * Do NOT inline this to getQuantity()
     */
    @NonNegative
    @JsonProperty
    private final long quantity = 1L;
    @NonNull
    @JsonProperty
    private PropertyMeta meta;

    @NotNull
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

    @Override
    @JsonIgnore
    public void setQuantity(long quantity) {
        // Does nothing; properties cannot be plural
    }

    @Override
    @JsonIgnore
    public void addQuantity(@NonNegative long delta) {
        // Does nothing; properties cannot be plural
    }

    @Override
    @JsonIgnore
    public void removeQuantity(@NonNegative long delta) {
        // Does nothing; properties cannot be plural
    }
    @NotNull
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
    private final AssetType type = AssetType.PROPERTY;

    @Override
    @JsonIgnore
    public @NonNull AssetType getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull PropertyStack copy() {
        return new PropertyStack(this);
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