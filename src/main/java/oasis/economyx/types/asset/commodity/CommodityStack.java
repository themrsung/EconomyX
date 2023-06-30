package oasis.economyx.types.asset.commodity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class CommodityStack implements AssetStack {
    public CommodityStack(@NonNull Commodity asset, @NonNegative long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new CommodityMeta();
    }

    public CommodityStack(@NotNull Commodity asset, @NonNegative long quantity, @NonNull CommodityMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public CommodityStack(CommodityStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    @NonNull
    @JsonProperty
    private final Commodity asset;
    @NonNegative
    @JsonProperty
    private long quantity;
    @NonNull
    @JsonProperty
    private CommodityMeta meta;

    @Override
    @JsonIgnore
    public @NonNull Commodity getAsset() {
        return new Commodity(asset);
    }

    @Override
    @JsonIgnore
    public @NonNegative long getQuantity() {
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

    @Override
    @JsonIgnore
    public @NonNull CommodityMeta getMeta() {
        return new CommodityMeta(meta);
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof CommodityMeta)) throw new IllegalArgumentException();

        this.meta = (CommodityMeta) meta;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.COMMODITY;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull CommodityStack copy() {
        return new CommodityStack(this);
    }

    /**
     * Commodity stacks require metadata to be equal
     * @param asset Commodity to compare to
     * @return Whether two commodities are the same
     * @throws IllegalArgumentException When a non-commodity stack is provided
     */
    @Override
    @JsonIgnore
    public boolean equals(AssetStack asset) throws IllegalArgumentException {
        return AssetStack.super.equals(asset) && getMeta().equals(asset.getMeta());
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    private CommodityStack() {
        this.asset = new Commodity();
        this.quantity = 0L;
        this.meta = new CommodityMeta();
    }
}
