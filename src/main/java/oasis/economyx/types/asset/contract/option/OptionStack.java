package oasis.economyx.types.asset.contract.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.contract.ContractStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

public final class OptionStack extends ContractStack {
    public OptionStack(@NonNull Option asset, @NonNegative long quantity) {
        super(asset, quantity, new OptionMeta());
    }

    public OptionStack(@NonNull Option asset, @NonNegative long quantity, @NonNull OptionMeta meta) {
        super(asset, quantity, meta);
    }

    public OptionStack(OptionStack other) {
        super(other);
    }

    @NonNull
    @Override
    @JsonIgnore
    public OptionMeta getMeta() {
        return (OptionMeta) meta;
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof OptionMeta)) throw new IllegalArgumentException();

        this.meta = (OptionMeta) meta;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.OPTION;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull OptionStack copy() {
        return new OptionStack(this);
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    public OptionStack() {
        super();
        this.meta = new OptionMeta();
    }
}
