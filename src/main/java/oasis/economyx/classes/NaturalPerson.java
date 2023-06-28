package oasis.economyx.classes;

import oasis.economyx.actor.person.Person;

/**
 * A natural person
 * Represents a player
 */
public final class NaturalPerson extends EconomicActor implements Person {
    public NaturalPerson() {
        super();
    }

    public NaturalPerson(NaturalPerson other) {
        super(other);
    }
}
