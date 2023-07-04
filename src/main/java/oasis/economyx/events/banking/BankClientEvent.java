package oasis.economyx.events.banking;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.banking.Account;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents an event between a bank and a client.
 */
public abstract class BankClientEvent extends EconomyEvent {
    public BankClientEvent(@NonNull Banker bank, @NonNull Account account) {
        this.bank = bank;
        this.account = account;
    }

    @NonNull
    private final Banker bank;

    @NonNull
    private final Account account;

    @NonNull
    public Banker getBank() {
        return bank;
    }

    @NonNull
    public Account getAccount() {
        return account;
    }
}
