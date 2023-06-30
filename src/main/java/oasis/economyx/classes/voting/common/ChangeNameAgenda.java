package oasis.economyx.classes.voting.common;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.voting.Agenda;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda to change the name of an actor.
 */
public final class ChangeNameAgenda implements Agenda {
    public ChangeNameAgenda(@NonNull Actor actor, @NonNull String newName) {
        this.actor = actor;
        this.newName = newName;
    }

    @Override
    @NonNull
    @JsonIgnore
    public String getDescription() {
        return "Change name of " + actor.getName() + " to " + newName;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Actor actor;
    @NonNull
    @JsonProperty
    private final String newName;

    @NonNull
    @JsonIgnore
    public Actor getActor() {
        return actor;
    }
    @NonNull
    @JsonIgnore
    public String getNewName() {
        return newName;
    }

    @Override
    @JsonIgnore
    public void run() {
        actor.setName(newName);
    }

    @JsonProperty
    private final Agenda.Type type = Type.CHANGE_NAME;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"actor", "newName"})
    private ChangeNameAgenda() {
        this.actor = null;
        this.newName = null;
    }
}
