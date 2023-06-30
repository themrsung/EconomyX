package oasis.economyx.events.banking;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an event between a bank and a client.
 */
public abstract class BankClientEvent extends EconomyEvent {
    public BankClientEvent(@NonNull Banker bank, @NonNull Account account, @NonNull AssetStack asset) {
        this.bank = bank;
        this.account = account;
        this.asset = asset;
    }

    @NonNull
    private final Banker bank;

    @NonNull
    private final Account account;

    @NonNull
    private final AssetStack asset;

    @NonNull
    public Banker getBank() {
        return bank;
    }

    @NonNull
    public Account getAccount() {
        return account;
    }

    @NonNull
    public AssetStack getAsset() {
        return asset;
    }
}
