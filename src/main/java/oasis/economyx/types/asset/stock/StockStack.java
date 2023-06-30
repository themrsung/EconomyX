package oasis.economyx.types.asset.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

public final class StockStack implements AssetStack {
    public StockStack(@NonNull Stock asset, long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new StockMeta();
    }

    public StockStack(@NonNull Stock asset, long quantity, @NonNull StockMeta meta) {
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
    @NonNegative
    @JsonProperty
    private long quantity;
    @NonNull
    @JsonProperty
    private StockMeta meta;

    @NonNull
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
    @NonNull
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

    @JsonProperty
    private final Asset.Type type = Asset.Type.STOCK;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull StockStack copy() {
        return new StockStack(this);
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
