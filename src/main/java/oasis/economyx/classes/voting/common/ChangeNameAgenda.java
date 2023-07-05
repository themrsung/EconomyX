package oasis.economyx.classes.voting.common;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.actor.ActorNameChangedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.interfaces.voting.Agenda;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda to change the name of an actor.
 */
public final class ChangeNameAgenda implements Agenda, References {
    public ChangeNameAgenda(@NonNull Actor actor, @NonNull String newName) {
        this.actor = actor;
        this.newName = newName;
    }

    @Override
    @NonNull
    @JsonIgnore
    public String getDescription() {
        return actor.getName() + "의 이름을 " + newName + "(으)로 변경합니다.";
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private Actor actor;
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
        Bukkit.getPluginManager().callEvent(new ActorNameChangedEvent(
                actor,
                newName
        ));
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

    @Override
    public void initialize(@NonNull EconomyState state) {
        for (Actor orig : state.getActors()) {
            if (orig.getUniqueId().equals(actor.getUniqueId())) {
                actor = orig;
                break;
            }
        }
    }
}
