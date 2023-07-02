package oasis.economyx.listeners.dividend;

import oasis.economyx.EconomyX;
import oasis.economyx.events.dividend.DividendEvent;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class DividendListener extends EconomyListener {
    public DividendListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onDividendPaid(DividendEvent e) {
        if (e.isCancelled()) return;

        final Shared payer = e.getPayer();
        final AssetStack dps = e.getDividendPerShare();

        for (Actor a : payer.getShareholders(getState())) {
            final AssetStack as = a.getAssets().get(new Stock(payer.getStockId()));
            if (as instanceof StockStack ss) {
                long q = ss.getQuantity();

                final AssetStack dividend = dps.copy();
                dividend.setQuantity(dividend.getQuantity() * q);

                Bukkit.getPluginManager().callEvent(new PaymentEvent(
                        payer,
                        a,
                        dividend,
                        PaymentEvent.Cause.DIVIDEND_PAYMENT
                ));
            }
        }
    }
}
