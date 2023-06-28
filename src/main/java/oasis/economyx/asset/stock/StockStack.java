package oasis.economyx.asset.stock;

import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class StockStack implements AssetStack {
    public StockStack(Stock asset, long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new StockMeta();
    }

    public StockStack(Stock asset, long quantity, StockMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public StockStack(StockStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    private final Stock asset;
    private long quantity;
    private StockMeta meta;

    @NotNull
    @Override
    public Stock getAsset() {
        return new Stock(asset);
    }

    @Override
    public long getQuantity() {
        return quantity;
    }

    @Override
    public void setQuantity(long quantity) {
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
    public StockMeta getMeta() {
        return new StockMeta(meta);
    }

    @Override
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof StockMeta)) throw new IllegalArgumentException();

        this.meta = (StockMeta) meta;
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    private StockStack() {
        this.asset = new Stock();
        this.quantity = 0L;
        this.meta = new StockMeta();
    }
}
