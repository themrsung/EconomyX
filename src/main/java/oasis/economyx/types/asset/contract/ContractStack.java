package oasis.economyx.types.asset.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class ContractStack implements AssetStack {
    public ContractStack() {
        this.asset = null;
        this.quantity = 0L;
        this.meta = null;
    }

    public ContractStack(@NonNull Contract asset, @NonNegative long quantity, @NonNull ContractMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public ContractStack(ContractStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    @NonNull
    @JsonProperty
    private final Contract asset;

    @NonNegative
    @JsonProperty
    private long quantity;

    @NonNull
    @JsonProperty
    protected ContractMeta meta;

    @NonNull
    @Override
    public Contract getAsset() {
        return asset;
    }

    @Override
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
    public abstract ContractMeta getMeta();

    @Override
    @JsonIgnore
    public abstract void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException;
}
