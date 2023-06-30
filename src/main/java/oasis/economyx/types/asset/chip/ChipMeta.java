package oasis.economyx.types.asset.chip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;

public final class ChipMeta implements AssetMeta {
    public ChipMeta() {
    }

    public ChipMeta(ChipMeta other) {
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.CHIP;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }
}
