package oasis.economyx.types.asset.contract.collateral;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;

public final class CollateralMeta implements AssetMeta{
    public CollateralMeta() {
    }

    public CollateralMeta(CollateralMeta other) {
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.COLLATERAL;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }
}
