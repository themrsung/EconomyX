package oasis.economyx.listeners.tax;

import oasis.economyx.EconomyX;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.events.tax.TaxRateChangedEvent;
import oasis.economyx.events.tax.TaxedEvent;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class TaxListener extends EconomyListener {
    public TaxListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTaxed(TaxedEvent e) {
        if (e.isCancelled()) return;

        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                e.getTaxPayer(),
                e.getTaxCollector(),
                e.getTax(),
                switch (e.getType()) {
                    case INCOME_TAX -> PaymentEvent.Cause.INCOME_TAX;
                    case INTEREST_TAX -> PaymentEvent.Cause.INTEREST_TAX;
                    case DIVIDEND_TAX -> PaymentEvent.Cause.DIVIDEND_TAX;
                }
        ));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTaxRateChanged(TaxRateChangedEvent e) {
        if (e.isCancelled()) return;

        final Sovereign s = e.getSovereign();
        final float r = e.getRate();

        switch (e.getType()) {
            case INCOME_TAX -> {
                s.setIncomeTaxRate(r);
            }
            case INTEREST_TAX -> {
                s.setInterestTaxRate(r);
            }
            case DIVIDEND_TAX -> {
                s.setDividendTaxRate(r);
            }
        }
    }
}
