package oasis.economyx.events.personal.representable;

import oasis.economyx.events.personal.PersonalEvent;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class RepresentableEvent extends PersonalEvent {
    public RepresentableEvent(@NonNull Person person, @NonNull Representable representable) {
        super(person);
        this.representable = representable;
    }

    @NonNull
    private final Representable representable;

    @NonNull
    public Representable getRepresentable() {
        return representable;
    }
}
