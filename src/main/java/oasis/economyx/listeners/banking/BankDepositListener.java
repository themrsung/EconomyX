package oasis.economyx.listeners.banking;

import oasis.economyx.EconomyX;
import oasis.economyx.events.banking.BankDepositEvent;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BankDepositListener extends EconomyListener {
    public BankDepositListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDeposit(BankDepositEvent e) {
        if (e.isCancelled()) return;

        final Account account = e.getAccount();
        final AssetStack asset = e.getAsset();

        account.deposit(asset);
    }
}
