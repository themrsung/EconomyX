package oasis.economyx.types.asset.cash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetMeta;

public final class CashMeta implements AssetMeta {
    public CashMeta() {
    }

    public CashMeta(CashMeta other) {
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.CASH;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }
}
