package oasis.economyx.events.personal.employment;

import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class EmployeeHiredEvent extends EmploymentEvent {
    public EmployeeHiredEvent(@NonNull Person person, @NonNull Employer employer) {
        super(person, employer);
    }
}
