package oasis.economyx.types.asset.contract.swap;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.meta.Purchasable;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

public final class SwapMeta implements AssetMeta, Purchasable {
    public SwapMeta() {
        this.purchasePrice = null;
        this.purchaseDate = null;
    }

    public SwapMeta(@Nullable CashStack purchasePrice, @Nullable DateTime purchaseDate) {
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
    }

    public SwapMeta(SwapMeta other) {
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
}
