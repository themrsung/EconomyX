package oasis.economyx.types.asset.contract.option;

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

    @Override
    public @NonNull Option getAsset() {
        if (!(super.getAsset() instanceof Option)) throw new RuntimeException();
        return (Option) super.getAsset();
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

    @Override
    public @NonNull String format(@NonNull EconomyState state) {
        return "["
                + (getAsset().getOptionType().isCall() ? "콜" : "풋")
                + "옵션] 기초자산 ["
                + getAsset().getDelivery().format(state)
                + "] " + "카운터파티: ["
                + (getAsset().getCounterparty().getName() != null ? getAsset().getCounterparty().getName() : "알 수 없음")
                + "] 수량: "
                + NumberFormat.getIntegerInstance().format(getQuantity())
                + "계약 유형: "
                + getAsset().getOptionType().toString();
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
