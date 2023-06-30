package oasis.economyx.types.asset.contract;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.meta.Purchasable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

public abstract class ContractMeta implements AssetMeta, Purchasable {
    public ContractMeta() {
        this.purchasePrice = null;
        this.purchaseDate = null;
    }

    public ContractMeta(@Nullable CashStack purchasePrice, @Nullable DateTime purchaseDate) {
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
    }

    public ContractMeta(ContractMeta other) {
        this.purchasePrice = other.purchasePrice;
        this.purchaseDate = other.purchaseDate;
    }

    @Nullable
    @JsonProperty
    private CashStack purchasePrice;
    @Nullable
    @JsonProperty
    private DateTime purchaseDate;

    @Override
    @JsonIgnore
    public @Nullable CashStack getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    @JsonIgnore
    public @Nullable DateTime getPurchaseDate() {
        return purchaseDate;
    }

    @Override
    @JsonIgnore
    public void setPurchasePrice(@Nullable CashStack price) {
        this.purchasePrice = price;
    }

    @Override
    @JsonIgnore
    public void setPurchaseDate(@Nullable DateTime date) {
        this.purchaseDate = date;
    }

    /**
     * Gets whether the holder can forgive this contract.
     * @return Whether this contract is forgivable
     */
    @JsonIgnore
    public abstract boolean isForgivable();
}
