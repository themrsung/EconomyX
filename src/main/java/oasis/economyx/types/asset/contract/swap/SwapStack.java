package oasis.economyx.types.asset.contract.swap;

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

public final class SwapStack extends ContractStack {
    public SwapStack(@NonNull Swap asset, @NonNegative long quantity) {
        super(asset, quantity, new SwapMeta());
    }

    public SwapStack(@NonNull Swap asset, @NonNegative long quantity, @NonNull SwapMeta meta) {
        super(asset, quantity, meta);
    }

    public SwapStack(SwapStack other) {
        super(other);
    }

    @Override
    public @NonNull Swap getAsset() {
        if (!(super.getAsset() instanceof Swap)) throw new RuntimeException();
        return (Swap) super.getAsset();
    }

    @NonNull
    @Override
    @JsonProperty("meta")
    public SwapMeta getMeta() {
        return (SwapMeta) meta;
    }

    @Override
    @JsonIgnore
    public void setMeta(@NonNull AssetMeta meta) throws IllegalArgumentException {
        if (!(meta instanceof SwapMeta)) throw new IllegalArgumentException();

        this.meta = (SwapMeta) meta;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.SWAP;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull SwapStack copy() {
        return new SwapStack(this);
    }

    @Override
    public @NonNull String format(@NonNull EconomyState state) {
        return "[스왑] 기준자산: ["
                + getAsset().getBase().getAsset().format(state)
                + "] 상대자산: ["
                + getAsset().getQuote().getAsset().format(state)
                + "] 수량: "
                + NumberFormat.getIntegerInstance().format(getQuantity())
                + "계약";
    }

    /**
     * Used for IO
     */
    @ConstructorProperties({"asset", "quantity", "meta"})
    public SwapStack() {
        super();
    }
}
