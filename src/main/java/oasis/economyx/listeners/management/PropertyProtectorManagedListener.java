package oasis.economyx.listeners.management;

import oasis.economyx.EconomyX;
import oasis.economyx.events.management.PropertyProtectionFeeChangedEvent;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PropertyProtectorManagedListener extends EconomyListener {
    public PropertyProtectorManagedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onProtectionFeeChanged(PropertyProtectionFeeChangedEvent e) {
        if (e.isCancelled()) return;

        e.getProtector().setProtectionFee(e.getFee());
    }
}
