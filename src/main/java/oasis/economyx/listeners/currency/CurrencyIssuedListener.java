package oasis.economyx.listeners.currency;

import oasis.economyx.EconomyX;
import oasis.economyx.events.currency.CurrencyIssuedEvent;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CurrencyIssuedListener extends EconomyListener {
    public CurrencyIssuedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCurrencyIssued(CurrencyIssuedEvent e) {
        if (e.isCancelled()) return;

        e.getIssuer().getAssets().add(new CashStack(e.getCurrency(), e.getAmount()));
    }
}
