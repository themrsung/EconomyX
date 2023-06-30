package oasis.economyx.events.actor;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.Actor;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class ActorEvent extends EconomyEvent {
    public ActorEvent(@NonNull Actor actor) {
        this.actor = actor;
    }

    @NonNull
    private final Actor actor;

    @NonNull
    public Actor getActor() {
        return actor;
    }
}
