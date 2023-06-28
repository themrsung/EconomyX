package oasis.economyx.classes.actor;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.actor.person.Person;
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
    private final ActorType type = ActorType.NATURAL_PERSON;

    @Override
    @JsonIgnore
    public ActorType getType() {
        return type;
    }
}
