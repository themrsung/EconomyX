package oasis.economyx.events.banking;

import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a bank withdrawal.
 */
public final class BankWithdrawalEvent extends BankClientEvent{
    public BankWithdrawalEvent(@NonNull Banker bank, @NonNull Account account, @NonNull AssetStack asset) {
        super(bank, account, asset);
    }
}
