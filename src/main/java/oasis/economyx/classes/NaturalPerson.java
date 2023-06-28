package oasis.economyx.classes;

import oasis.economyx.actor.person.Person;
import oasis.economyx.portfolio.Portfolio;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * A natural person
 * Represents a player
 */
public final class NaturalPerson extends EconomicActor implements Person {
    public NaturalPerson() {
        super();
    }

    public NaturalPerson(UUID uniqueId, @Nullable String name, Portfolio portfolio) {
        super(uniqueId, name, portfolio);
    }

    public NaturalPerson(EconomicActor other) {
        super(other);
    }
}
