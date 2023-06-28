package oasis.economyx.asset.contract.collateral;

import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.contract.forward.Forward;
import oasis.economyx.asset.contract.forward.ForwardMeta;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class CollateralStack implements AssetStack {
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
    private final Collateral asset;
    @NonNegative
    private long quantity;
    @NonNull
    private CollateralMeta meta;

    @NotNull
    @Override
    public Collateral getAsset() {
        return asset;
    }

    @Override
    public long getQuantity() {
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

    @NotNull
    @Override
    public CollateralMeta getMeta() {
        return meta;
    }

    @Override
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof CollateralMeta)) throw new IllegalArgumentException();

        this.meta = (CollateralMeta) meta;
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
