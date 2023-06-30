package oasis.economyx.events.actor;

import oasis.economyx.interfaces.actor.Actor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ActorNameChangedEvent extends ActorEvent {
    public ActorNameChangedEvent(@NonNull Actor actor, @Nullable String name) {
        super(actor);
        this.name = name;
    }

    @Nullable
    private final String name;

    @Nullable
    public String getName() {
        return name;
    }
}
