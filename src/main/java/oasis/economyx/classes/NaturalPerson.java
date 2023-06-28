package oasis.economyx.classes;

import oasis.economyx.actor.person.Person;
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
}
