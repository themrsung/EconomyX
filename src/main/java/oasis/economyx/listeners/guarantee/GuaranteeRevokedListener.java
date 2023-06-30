package oasis.economyx.listeners.guarantee;

import oasis.economyx.EconomyX;
import oasis.economyx.events.guarantee.GuaranteeIssuedEvent;
import oasis.economyx.interfaces.actor.types.finance.Credible;
import oasis.economyx.listeners.EconomyListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class GuaranteeRevokedListener extends EconomyListener {
    public GuaranteeRevokedListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onGuaranteeRevoked(GuaranteeIssuedEvent e) {
        if (e.isCancelled()) return;

        Credible guarantor =  e.getGuarantor();
        guarantor.removeGuarantee(e.getGuarantee());
    }
}
