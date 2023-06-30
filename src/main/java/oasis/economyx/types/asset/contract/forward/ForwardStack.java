package oasis.economyx.types.asset.contract.forward;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

public final class ForwardStack implements AssetStack {
    public ForwardStack(@NonNull Forward asset, @NonNegative long quantity) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = new ForwardMeta();
    }

    public ForwardStack(@NonNull Forward asset, @NonNegative long quantity, @NonNull ForwardMeta meta) {
        this.asset = asset;
        this.quantity = quantity;
        this.meta = meta;
    }

    public ForwardStack(ForwardStack other) {
        this.asset = other.asset;
        this.quantity = other.quantity;
        this.meta = other.meta;
    }

    @NonNull
    @JsonProperty
    private final Forward asset;
    @NonNegative
    @JsonProperty
    private long quantity;
    @NonNull
    @JsonProperty
    private ForwardMeta meta;

    @NonNull
    @Override
    @JsonIgnore
    public Forward getAsset() {
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
    public ForwardMeta getMeta() {
        return meta;
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof ForwardMeta)) throw new IllegalArgumentException();

        this.meta = (ForwardMeta) meta;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.FORWARD;

    @JsonIgnore
    @Override
    public Asset.Type getType() {
        return type;
    }

    @Override
    public @NonNull ForwardStack copy() {
        return new ForwardStack(this);
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    public ForwardStack() {
        this.asset = new Forward();
        this.quantity = 0L;
        this.meta = new ForwardMeta();
    }
}
