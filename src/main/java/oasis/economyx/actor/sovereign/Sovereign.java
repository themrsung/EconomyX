package oasis.economyx.actor.sovereign;

import oasis.economyx.actor.corporation.Corporation;
import oasis.economyx.actor.person.Person;
import oasis.economyx.actor.types.services.Protector;
import oasis.economyx.actor.types.governance.Representable;

import java.util.List;

/**
 * A sovereign actor must be represented by a person, but is not owned by any other actor
 */
public interface Sovereign extends Representable, Protector {
    List<Person> getCitizens();
    void addCitizen(Person citizen);
    void removeCitizen(Person citizen);

    List<Corporation> getCorporations();
    void addCorporation(Corporation corporation);
    void removeCorporation(Corporation corporation);
}
