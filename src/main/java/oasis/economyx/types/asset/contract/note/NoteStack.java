package oasis.economyx.types.asset.contract.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.contract.ContractStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

public final class NoteStack extends ContractStack {
    public NoteStack(@NonNull Note asset, @NonNegative long quantity) {
        super(asset, quantity, new NoteMeta());
    }

    public NoteStack(@NonNull Note asset, @NonNegative long quantity, @NonNull NoteMeta meta) {
        super(asset, quantity, meta);
    }

    public NoteStack(NoteStack other) {
        super(other);
    }

    @NonNull
    @Override
    @JsonIgnore
    public NoteMeta getMeta() {
        return (NoteMeta) meta;
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
        super();
    }
}
