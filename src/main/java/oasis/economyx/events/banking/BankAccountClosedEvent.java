package oasis.economyx.events.banking;

import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.banking.Account;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BankAccountClosedEvent extends BankClientEvent {
    public BankAccountClosedEvent(@NonNull Banker bank, @NonNull Account account) {
        super(bank, account);
    }
}
