package oasis.economyx.types.asset.contract.collateral;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.contract.ContractMeta;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

public final class CollateralMeta extends ContractMeta {
    public CollateralMeta() {
        super();
    }

    public CollateralMeta(@Nullable CashStack purchasePrice, @Nullable DateTime purchaseDate) {
        super(purchasePrice, purchaseDate);
    }

    public CollateralMeta(CollateralMeta other) {
        super(other);
    }

    @Override
    @JsonIgnore
    public boolean isForgivable() {
        return true;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.COLLATERAL;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }
}
