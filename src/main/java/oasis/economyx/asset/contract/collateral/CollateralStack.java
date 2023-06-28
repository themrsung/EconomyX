package oasis.economyx.asset.contract.collateral;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.AssetStackType;
import oasis.economyx.asset.AssetType;
import oasis.economyx.asset.contract.forward.Forward;
import oasis.economyx.asset.contract.forward.ForwardMeta;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class CollateralStack implements AssetStack {
    public CollateralStack(@NonNull Collateral asset, @NonNegative long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new CollateralMeta();
    }

    public CollateralStack(@NonNull Collateral asset, @NonNegative long quantity, @NonNull CollateralMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public CollateralStack(CollateralStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    @NonNull
    @JsonProperty
    private final Collateral asset;
    @NonNegative
    @JsonProperty
    private long quantity;
    @NonNull
    @JsonProperty
    private CollateralMeta meta;

    @NotNull
    @Override
    @JsonIgnore
    public Collateral getAsset() {
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
    @JsonIgnore
    public CollateralMeta getMeta() {
        return meta;
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof CollateralMeta)) throw new IllegalArgumentException();

        this.meta = (CollateralMeta) meta;
    }

    @JsonProperty
    private final AssetType type = AssetType.COLLATERAL;
    @Override
    @JsonIgnore
    public @NonNull AssetType getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull CollateralStack copy() {
        return new CollateralStack(this);
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    public CollateralStack() {
        this.asset = new Collateral();
        this.quantity = 0L;
        this.meta = new CollateralMeta();
    }
}
