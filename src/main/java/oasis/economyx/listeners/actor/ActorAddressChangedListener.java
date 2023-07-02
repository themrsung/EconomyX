package oasis.economyx.listeners.actor;

import oasis.economyx.EconomyX;
import oasis.economyx.events.actor.ActorAddressChangedEvent;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ActorAddressChangedListener extends EconomyListener {
    public ActorAddressChangedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAddressChanged(ActorAddressChangedEvent e) {
        if (e.isCancelled()) return;

        e.getActor().setAddress(e.getAddress());
    }
}
