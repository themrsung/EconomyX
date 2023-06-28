package oasis.economyx.asset.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.AssetStackType;
import oasis.economyx.asset.AssetType;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class StockStack implements AssetStack {
    public StockStack(@NotNull Stock asset, long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new StockMeta();
    }

    public StockStack(@NotNull Stock asset, long quantity, @NotNull StockMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public StockStack(StockStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    @NonNull
    @JsonProperty
    private final Stock asset;
    @NonNull
    @JsonProperty
    private long quantity;
    @NonNull
    @JsonProperty
    private StockMeta meta;

    @NotNull
    @Override
    @JsonIgnore
    public Stock getAsset() {
        return new Stock(asset);
    }

    @Override
    @JsonIgnore
    public long getQuantity() {
        return quantity;
    }

    @Override
    @JsonIgnore
    public void setQuantity(long quantity) {
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
    public StockMeta getMeta() {
        return new StockMeta(meta);
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof StockMeta)) throw new IllegalArgumentException();

        this.meta = (StockMeta) meta;
    }

    private final AssetType type = AssetType.STOCK;

    @Override
    public @NonNull AssetType getType() {
        return type;
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
