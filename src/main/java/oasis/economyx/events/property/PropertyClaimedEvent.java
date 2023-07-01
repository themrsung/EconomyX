package oasis.economyx.events.property;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.property.PropertyStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PropertyClaimedEvent extends PropertyEvent {
    public PropertyClaimedEvent(@NonNull PropertyStack property, @NonNull Actor claimer) {
        super(property);
        this.claimer = claimer;
    }

    @NonNull
    private final Actor claimer;

    @NonNull
    public Actor getClaimer() {
        return claimer;
    }
}
