package oasis.economyx.classes.voting.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.voting.Agenda;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda that does nothing. Used for pro status quo votes.
 */
public final class DummyAgenda implements Agenda {

    public DummyAgenda(@NonNull String description) {
        this.description = description;
    }

    @JsonProperty
    @NonNull
    private final String description;

    @NonNull
    @Override
    @JsonIgnore
    public String getDescription() {
        return description;
    }

    @Override
    @JsonIgnore
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

    @ConstructorProperties({"description"})
    private DummyAgenda() {
        this.description = null;
    }
}
