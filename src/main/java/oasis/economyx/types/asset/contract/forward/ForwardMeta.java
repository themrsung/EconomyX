package oasis.economyx.types.asset.contract.forward;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.contract.ContractMeta;
import oasis.economyx.types.asset.meta.Purchasable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

public final class ForwardMeta extends ContractMeta implements Purchasable {
    public ForwardMeta() {
        super();
    }

    public ForwardMeta(@Nullable CashStack purchasePrice, @Nullable DateTime purchaseDate) {
        super(purchasePrice, purchaseDate);
    }

    public ForwardMeta(ForwardMeta other) {
        super(other);
    }

    @Override
    @JsonIgnore
    public boolean isForgivable() {
        return false;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.FORWARD;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }
}
