package oasis.economyx.events.personal.employment;

import oasis.economyx.events.personal.PersonalEvent;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class EmploymentEvent extends PersonalEvent {
    public EmploymentEvent(@NonNull Person person, @NonNull Employer employer) {
        super(person);
        this.employer = employer;
    }

    @NonNull
    private final Employer employer;

    @NonNull
    public Employer getEmployer() {
        return employer;
    }
}
