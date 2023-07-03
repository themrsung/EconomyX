package oasis.economyx.types.asset.contract.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.contract.Contract;
import oasis.economyx.types.asset.contract.ContractStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;
import java.text.NumberFormat;

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

    @Override
    public @NonNull Note getAsset() {
        return (Note) super.getAsset();
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

    @Override
    public @NonNull String format(@NonNull EconomyState state) {
        return "[어음] 기초자산 ["
                + getAsset().getDelivery().format(state)
                + "] " + "채무자: ["
                + (getAsset().getCounterparty().getName() != null ? getAsset().getCounterparty().getName() : "알 수 없음")
                + "] 수량: "
                + NumberFormat.getIntegerInstance().format(getQuantity())
                + "계약";
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    public NoteStack() {
        super();
    }
}
