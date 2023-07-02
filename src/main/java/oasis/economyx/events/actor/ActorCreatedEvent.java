package oasis.economyx.events.actor;

import oasis.economyx.interfaces.actor.Actor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * Represents the creation of an actor.
 */
public final class ActorCreatedEvent extends ActorEvent {
    public ActorCreatedEvent(@NonNull Actor actor, @Nullable Actor founder, Type type) {
        super(actor);

        this.founder = founder;
        this.type = type;
    }

    @Nullable
    private final Actor founder;
    private final Type type;

    public Actor getFounder() {
        return founder;
    }

    public Type getType() {
        return type;
    }

    public enum Type {
        /**
         * When a player joins for the first time
         */
        PLAYER_PROFILE_CREATED,

        /**
         * When a player creates another actor
         */
        CREATED_BY_PLAYER,

        /**
         * When the server creates an actor
         */
        CREATED_BY_SERVER
    }
}
