package oasis.economyx.events.personal.representable;

import oasis.economyx.interfaces.actor.types.governance.Representable;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class RepresentativeFiredEvent extends RepresentableEvent {
    public RepresentativeFiredEvent(@NonNull Representable representable) {
        super(null, representable); // TODO Make PersonalEvent.person nullable
    }
}
