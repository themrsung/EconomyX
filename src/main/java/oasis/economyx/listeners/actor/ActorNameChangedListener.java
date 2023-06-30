package oasis.economyx.listeners.actor;

import oasis.economyx.EconomyX;
import oasis.economyx.events.actor.ActorNameChangedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listeners.EconomyListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ActorNameChangedListener extends EconomyListener {
    public ActorNameChangedListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onActorNameChanged(ActorNameChangedEvent e) {
        if (e.isCancelled()) return;

        Actor a = e.getActor();
        String n = e.getName();

        a.setName(n);
    }
}
