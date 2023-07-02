package oasis.economyx.listeners.banking;

import oasis.economyx.EconomyX;
import oasis.economyx.events.banking.BankAccountClosedEvent;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BankAccountClosedListener extends EconomyListener {
    public BankAccountClosedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBankAccountClosed(BankAccountClosedEvent e) {
        if (e.isCancelled()) return;

        Banker bank = e.getBank();
        Account account = e.getAccount();

        account.withdraw(account.getContent());
        bank.removeAccount(account);
    }
}
