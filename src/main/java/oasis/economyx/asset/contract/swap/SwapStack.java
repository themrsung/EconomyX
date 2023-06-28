package oasis.economyx.asset.contract.swap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.AssetStackType;
import oasis.economyx.asset.AssetType;
import oasis.economyx.asset.contract.option.Option;
import oasis.economyx.asset.contract.option.OptionMeta;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class SwapStack implements AssetStack {
    public SwapStack(@NonNull Swap asset, @NonNegative long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new SwapMeta();
    }

    public SwapStack(@NonNull Swap asset, @NonNegative long quantity, @NonNull SwapMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public SwapStack(SwapStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    @NonNull
    @JsonProperty
    private final Swap asset;
    @NonNegative
    @JsonProperty
    private long quantity;
    @NonNull
    @JsonProperty
    private SwapMeta meta;

    @NotNull
    @Override
    @JsonIgnore
    public Swap getAsset() {
        return asset;
    }

    @Override
    @JsonIgnore
    public long getQuantity() {
        return quantity;
    }

    @Override
    @JsonIgnore
    public void setQuantity(@NonNegative long quantity) {
        this.quantity = quantity;
    }

    @Override
    @JsonIgnore
    public void addQuantity(@NonNegative long delta) {
        this.quantity += delta;
    }

    @Override
    @JsonIgnore
    public void removeQuantity(@NonNegative long delta) throws IllegalArgumentException {
        if (this.quantity - delta < 0L) throw new IllegalArgumentException();

        this.quantity -= delta;
    }

    @NotNull
    @Override
    @JsonProperty("meta")
    public SwapMeta getMeta() {
        return meta;
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof SwapMeta)) throw new IllegalArgumentException();

        this.meta = (SwapMeta) meta;
    }

    @JsonProperty
    private final AssetType type = AssetType.SWAP;

    @Override
    @JsonIgnore
    public @NonNull AssetType getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull SwapStack copy() {
        return new SwapStack(this);
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    public SwapStack() {
        this.asset = new Swap();
        this.quantity = 0L;
        this.meta = new SwapMeta();
    }
}
