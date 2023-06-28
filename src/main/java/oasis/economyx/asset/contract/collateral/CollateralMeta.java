package oasis.economyx.asset.contract.collateral;

import oasis.economyx.asset.AssetMeta;
import oasis.economyx.asset.Purchasable;
import oasis.economyx.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

public final class CollateralMeta implements AssetMeta, Purchasable {
    public CollateralMeta() {
        this.purchasePrice = null;
        this.purchaseDate = null;
    }

    public CollateralMeta(@Nullable CashStack purchasePrice, @Nullable DateTime purchaseDate) {
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
    }

    public CollateralMeta(CollateralMeta other) {
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
