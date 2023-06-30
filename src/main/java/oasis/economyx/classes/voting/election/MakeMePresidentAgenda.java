package oasis.economyx.classes.voting.election;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.voting.Agenda;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda for a presidential candidate.
 */
public final class MakeMePresidentAgenda implements Agenda {
    public MakeMePresidentAgenda(@NonNull Sovereign sovereign, @NonNull Person candidate) {
        this.sovereign = sovereign;
        this.candidate = candidate;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Sovereign sovereign;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Person candidate;

    @NonNull
    @JsonIgnore
    public Sovereign getSovereign() {
        return sovereign;
    }

    @NonNull
    @JsonIgnore
    public Person getCandidate() {
        return candidate;
    }

    @NonNull
    @Override
    @JsonIgnore
    public String getDescription() {
        return "Make " + candidate.getName() + " the president of " + sovereign.getName();
    }

    @Override
    @JsonIgnore
    public void run() {
        sovereign.setRepresentative(candidate);
    }

    @JsonProperty
    private final Type type = Type.MAKE_ME_PRESIDENT;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"sovereign", "candidate"})
    private MakeMePresidentAgenda() {
        this.sovereign = null;
        this.candidate = null;
    }
}
