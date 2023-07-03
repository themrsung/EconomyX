package oasis.economyx.types.asset.contract.forward;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.contract.ContractStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;
import java.text.NumberFormat;

public final class ForwardStack extends ContractStack {
    public ForwardStack(@NonNull Forward asset, @NonNegative long quantity) {
        super(asset, quantity, new ForwardMeta());
    }

    public ForwardStack(@NonNull Forward asset, @NonNegative long quantity, @NonNull ForwardMeta meta) {
        super(asset, quantity, meta);
    }

    public ForwardStack(ForwardStack other) {
        super(other);
    }

    @NonNull
    @Override
    @JsonIgnore
    public Forward getAsset() {
        return (Forward) super.getAsset();
    }

    @NonNull
    @Override
    @JsonIgnore
    public ForwardMeta getMeta() {
        return (ForwardMeta) meta;
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

    @Override
    public @NonNull String format(@NonNull EconomyState state) {
        return "[선도계약] 기초자산 ["
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
    public ForwardStack() {
        super();
        this.meta = new ForwardMeta();
    }
}
