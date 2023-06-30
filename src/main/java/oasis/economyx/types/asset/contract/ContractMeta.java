package oasis.economyx.types.asset.contract;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.meta.Purchasable;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

public abstract class ContractMeta implements AssetMeta, Purchasable {
    public ContractMeta(@Nullable CashStack purchasePrice, @Nullable DateTime purchaseDate, @NonNull Actor counterparty) {
        this.purchasePrice = purchasePrice;
        this.purchaseDate = purchaseDate;
        this.counterparty = counterparty;
    }

    public ContractMeta(ContractMeta other) {
        this.purchasePrice = other.purchasePrice;
        this.purchaseDate = other.purchaseDate;
        this.counterparty = other.counterparty;
    }

    @Nullable
    private CashStack purchasePrice;
    @Nullable
    private DateTime purchaseDate;
    @NonNull
    private Actor counterparty;

    @Override
    public @Nullable CashStack getPurchasePrice() {
        return purchasePrice;
    }

    @Override
    public @Nullable DateTime getPurchaseDate() {
        return purchaseDate;
    }

    @NonNull
    public Actor getCounterparty() {
        return counterparty;
    }

    @Override
    public void setPurchasePrice(@Nullable CashStack price) {
        this.purchasePrice = price;
    }

    @Override
    public void setPurchaseDate(@Nullable DateTime date) {
        this.purchaseDate = date;
    }

    public void setCounterparty(@NonNull Actor counterparty) {
        this.counterparty = counterparty;
    }
}
