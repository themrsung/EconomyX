package oasis.economyx.events.personal.representable;

import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class RepresentativeHiredEvent extends RepresentableEvent {
    public RepresentativeHiredEvent(@NonNull Person person, @NonNull Representable representable) {
        super(person, representable);
    }
}
