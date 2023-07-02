package oasis.economyx.events.banking;

import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a bank deposit.
 */
public final class BankDepositEvent extends BankClientEvent {
    public BankDepositEvent(@NonNull Banker bank, @NonNull Account account, @NonNull AssetStack asset) {
        super(bank, account);

        this.asset = asset;
    }

    @NonNull
    private final AssetStack asset;

    @NonNull
    public AssetStack getAsset() {
        return asset;
    }
}
