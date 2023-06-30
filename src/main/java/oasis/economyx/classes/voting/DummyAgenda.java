package oasis.economyx.classes.voting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.voting.Agenda;

/**
 * An agenda that does nothing. Used for pro status quo votes.
 */
public final class DummyAgenda implements Agenda {
    @Override
    public void run() {
        // Do nothing
    }

    @JsonProperty
    private final Agenda.Type type = Type.DUMMY;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }
}
