package oasis.economyx.actor.organization;

import oasis.economyx.actor.person.Person;
import oasis.economyx.actor.types.services.Protector;
import oasis.economyx.actor.types.governance.Representable;

import java.util.List;

public interface Military extends Representable, Protector {
    List<Person> getCombatants();
    void addCombatant(Person combatant);
    void removeCombatant(Person combatant);


}
