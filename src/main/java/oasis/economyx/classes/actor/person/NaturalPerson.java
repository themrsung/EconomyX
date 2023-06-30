package oasis.economyx.classes.actor.person;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.classes.EconomicActor;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * The instantiable class of Person
 */
public final class NaturalPerson extends EconomicActor implements Person {
    /**
     * Constructs a new natural person
     * @param uniqueId Unique ID of this person
     * @param name Name of this person (not unique)
     */
    public NaturalPerson(UUID uniqueId, @Nullable String name) {
        super(uniqueId, name);
    }

    public NaturalPerson() {
        super();
    }

    public NaturalPerson(NaturalPerson other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.NATURAL_PERSON;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }
}
