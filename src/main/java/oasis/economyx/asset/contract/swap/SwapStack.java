package oasis.economyx.asset.contract.swap;

import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.contract.option.Option;
import oasis.economyx.asset.contract.option.OptionMeta;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class SwapStack implements AssetStack {
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
    private final Swap asset;
    @NonNegative
    private long quantity;
    @NonNull
    private SwapMeta meta;

    @NotNull
    @Override
    public Swap getAsset() {
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
    public SwapMeta getMeta() {
        return meta;
    }

    @Override
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof SwapMeta)) throw new IllegalArgumentException();

        this.meta = (SwapMeta) meta;
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
