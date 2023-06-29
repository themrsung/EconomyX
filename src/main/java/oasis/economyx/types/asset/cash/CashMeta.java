package oasis.economyx.types.asset.cash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public final class CashMeta implements AssetMeta {
    public CashMeta() {
    }

    public CashMeta(CashMeta other) {
    }

    @NonNull
    @JsonProperty
    private final AssetType type = AssetType.CASH;

    @NotNull
    @Override
    @JsonIgnore
    public AssetType getType() {
        return type;
    }
}
