package oasis.economyx.asset.commodity;

import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class CommodityStack implements AssetStack {
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
    private final Commodity asset;
    @NonNegative
    private long quantity;
    @NonNull
    private CommodityMeta meta;

    @Override
    public @NonNull Commodity getAsset() {
        return new Commodity(asset);
    }

    @Override
    public @NonNegative long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(@NonNegative long quantity) {
        this.quantity = quantity;
    }

    @Override
    public void addQuantity(@NonNegative long delta) {
        this.quantity += delta;
    }

    @Override
    public void removeQuantity(@NonNegative long delta) throws IllegalArgumentException {
        if (this.quantity - delta < 0L) throw new IllegalArgumentException();

        this.quantity -= delta;
    }

    @Override
    public @NonNull CommodityMeta getMeta() {
        return new CommodityMeta(meta);
    }

    @Override
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof CommodityMeta)) throw new IllegalArgumentException();

        this.meta = (CommodityMeta) meta;
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
