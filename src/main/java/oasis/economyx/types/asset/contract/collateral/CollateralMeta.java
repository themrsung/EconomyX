package oasis.economyx.types.asset.contract.collateral;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.jetbrains.annotations.NotNull;

public final class CollateralMeta implements AssetMeta{
    public CollateralMeta() {
    }

    public CollateralMeta(CollateralMeta other) {
    }

    @NonNull
    @JsonProperty
    private final AssetType type = AssetType.COLLATERAL;

    @NotNull
    @Override
    @JsonIgnore
    public AssetType getType() {
        return type;
    }
}
