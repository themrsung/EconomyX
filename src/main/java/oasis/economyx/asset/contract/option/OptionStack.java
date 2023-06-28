package oasis.economyx.asset.contract.option;

import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.contract.note.Note;
import oasis.economyx.asset.contract.note.NoteMeta;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class OptionStack implements AssetStack {
    public OptionStack(@NonNull Option asset, @NonNegative long quantity, @NonNull OptionMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public OptionStack(OptionStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    @NonNull
    private final Option asset;
    @NonNegative
    private long quantity;
    @NonNull
    private OptionMeta meta;

    @NotNull
    @Override
    public Option getAsset() {
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
    public OptionMeta getMeta() {
        return meta;
    }

    @Override
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof OptionMeta)) throw new IllegalArgumentException();

        this.meta = (OptionMeta) meta;
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    public OptionStack() {
        this.asset = new Option();
        this.quantity = 0L;
        this.meta = new OptionMeta();
    }
}
