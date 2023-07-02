package oasis.economyx.listeners.personal;

import oasis.economyx.EconomyX;
import oasis.economyx.events.personal.representable.RepresentativeFiredEvent;
import oasis.economyx.events.personal.representable.RepresentativeHiredEvent;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class RepresentableEventListener extends EconomyListener {
    public RepresentableEventListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRepresentativeHired(RepresentativeHiredEvent e) {
        if (e.isCancelled()) return;

        e.getRepresentable().setRepresentative(e.getPerson());
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onRepresentativeFired(RepresentativeFiredEvent e) {
        if (e.isCancelled()) return;

        e.getRepresentable().setRepresentative(null);
    }
}
