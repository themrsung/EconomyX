package oasis.economyx.types.asset.contract.note;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.contract.ContractMeta;
import oasis.economyx.types.asset.meta.Purchasable;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

public final class NoteMeta extends ContractMeta implements Purchasable {
    public NoteMeta() {
        super();
    }

    public NoteMeta(@Nullable CashStack purchasePrice, @Nullable DateTime purchaseDate) {
        super(purchasePrice, purchaseDate);
    }

    public NoteMeta(NoteMeta other) {
        super(other);
    }

    @Override
    @JsonIgnore
    public boolean isForgivable() {
        return true;
    }

    @JsonProperty
    private final Asset.Type type = Asset.Type.NOTE;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }
}
