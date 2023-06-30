package oasis.economyx.types.asset.contract.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

public final class NoteStack implements AssetStack {
    public NoteStack(@NonNull Note asset, @NonNegative long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new NoteMeta();
    }

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
    @JsonProperty
    private final Note asset;
    @NonNegative
    @JsonProperty
    private long quantity;
    @NonNull
    @JsonProperty
    private NoteMeta meta;

    @NonNull
    @Override
    @JsonIgnore
    public Note getAsset() {
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

    @NonNull
    @Override
    @JsonIgnore
    public NoteMeta getMeta() {
        return meta;
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof NoteMeta)) throw new IllegalArgumentException();

        this.meta = (NoteMeta) meta;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.NOTE;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull NoteStack copy() {
        return new NoteStack(this);
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
