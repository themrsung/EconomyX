package oasis.economyx.listeners.actor;

import oasis.economyx.EconomyX;
import oasis.economyx.events.actor.ActorCreatedEvent;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Handles actor creation.
 */
public final class ActorCreationListener extends EconomyListener {
    public ActorCreationListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onActorCreated(ActorCreatedEvent e) {
        if (e.isCancelled()) return;

        getState().addActor(e.getActor());
        if (e.getActor() instanceof Institutional i) {
            i.getParent().addInstitution(i);
        }
    }
}
