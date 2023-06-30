package oasis.economyx.classes.voting;

import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.voting.Agenda;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda to change the name of an actor.
 */
public class ChangeNameAgenda implements Agenda {
    public ChangeNameAgenda(@NonNull Actor actor, @NonNull String newName) {
        this.actor = actor;
        this.newName = newName;
    }

    @NonNull
    private final Actor actor;
    @NonNull
    private final String newName;

    @NonNull
    public Actor getActor() {
        return actor;
    }
    @NonNull
    public String getNewName() {
        return newName;
    }

    @Override
    public void run() {
        actor.setName(newName);
    }

    @JsonProperty
    private final Agenda.Type type = Type.CHANGE_NAME;

    @Override
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"actor", "newName"})
    private ChangeNameAgenda() {
        this.actor = null;
        this.newName = null;
    }
}