package oasis.economyx.events.personal;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.person.Person;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class PersonalEvent extends EconomyEvent {
    public PersonalEvent(@NonNull Person person) {
        this.person = person;
    }

    @NonNull
    private final Person person;

    @NonNull
    public Person getPerson() {
        return person;
    }
}
