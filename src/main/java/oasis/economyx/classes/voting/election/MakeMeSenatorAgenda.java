package oasis.economyx.classes.voting.election;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.institution.tripartite.Legislature;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.voting.Agenda;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda for a legislative candidate.
 */
public final class MakeMeSenatorAgenda implements Agenda {
    public MakeMeSenatorAgenda(@NonNull Legislature legislature, @NonNull Person candidate) {
        this.legislature = legislature;
        this.candidate = candidate;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Legislature legislature;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Person candidate;

    @NonNull
    @JsonIgnore
    public Legislature getLegislature() {
        return legislature;
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
        return "Make " + candidate.getName() + " a member of " + legislature.getName();
    }

    @Override
    @JsonIgnore
    public void run() {
        legislature.addDirector(candidate); // Directors of a legislature are considered to be senators
    }

    @JsonProperty
    private final Type type = Type.MAKE_ME_SENATOR;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"legislature", "candidate"})
    private MakeMeSenatorAgenda() {
        this.legislature = null;
        this.candidate = null;
    }
}
