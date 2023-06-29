package oasis.economyx.types.asset.chip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public final class ChipMeta implements AssetMeta {
    public ChipMeta() {
    }

    public ChipMeta(ChipMeta other) {
    }

    @NonNull
    @JsonProperty
    private final AssetType type = AssetType.CHIP;

    @NotNull
    @Override
    @JsonIgnore
    public AssetType getType() {
        return type;
    }
}
