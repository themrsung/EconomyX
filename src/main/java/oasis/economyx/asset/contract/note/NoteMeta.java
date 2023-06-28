package oasis.economyx.asset.contract.note;

import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.Purchasable;
import oasis.economyx.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

public final class NoteMeta implements AssetMeta, Purchasable {
    public NoteMeta() {
        this.purchasePrice = null;
        this.purchaseDate = null;
    }

    public NoteMeta(@Nullable CashStack purchasePrice, @Nullable DateTime purchaseDate) {
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
    }

    public NoteMeta(NoteMeta other) {
        this.purchasePrice = other.purchasePrice;
        this.purchaseDate = other.purchaseDate;
    }

    @Nullable
    private CashStack purchasePrice;

    @Nullable
    private DateTime purchaseDate;


    @Override
    public @Nullable CashStack getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public @Nullable DateTime getPurchaseDate() {
        return purchaseDate;
    }

    @Override
    public void setPurchasePrice(@Nullable CashStack price) {
        this.purchasePrice = price;
    }

    @Override
    public void setPurchaseDate(@Nullable DateTime date) {
        this.purchaseDate = date;
    }
}
