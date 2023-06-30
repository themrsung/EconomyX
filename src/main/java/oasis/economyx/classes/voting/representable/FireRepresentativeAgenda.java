package oasis.economyx.classes.voting.representable;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.voting.Agenda;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda to fire the representative of a representable
 */
public final class FireRepresentativeAgenda implements Agenda {
    public FireRepresentativeAgenda(@NonNull Representable representable) {
        this.representable = representable;
    }

    @Override
    @JsonIgnore
    public @NonNull String getDescription() {
        return "Fire the representative of " + representable.getName();
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Representable representable;

    @NonNull
    @JsonIgnore
    public Representable getRepresentable() {
        return representable;
    }

    @Override
    @JsonIgnore
    public void run() {
        representable.setRepresentative(null);
    }

    @JsonProperty
    private final Type type = Type.FIRE_REPRESENTATIVE;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"representable"})
    private FireRepresentativeAgenda() {
        this.representable = null;
    }
}
