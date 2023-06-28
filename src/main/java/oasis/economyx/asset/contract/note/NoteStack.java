package oasis.economyx.asset.contract.note;

import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.contract.forward.Forward;
import oasis.economyx.asset.contract.forward.ForwardMeta;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

import java.beans.ConstructorProperties;

public final class NoteStack implements AssetStack {
    public NoteStack(@NonNull Note asset, @NonNegative long quantity, @NonNull NoteMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public NoteStack(NoteStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    @NonNull
    private final Note asset;
    @NonNegative
    private long quantity;
    @NonNull
    private NoteMeta meta;

    @NotNull
    @Override
    public Note getAsset() {
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
    public NoteMeta getMeta() {
        return meta;
    }

    @Override
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof NoteMeta)) throw new IllegalArgumentException();

        this.meta = (NoteMeta) meta;
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    public NoteStack() {
        this.asset = new Note();
        this.quantity = 0L;
        this.meta = new NoteMeta();
    }
}
