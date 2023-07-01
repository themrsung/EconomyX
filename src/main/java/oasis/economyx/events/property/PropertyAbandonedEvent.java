package oasis.economyx.events.property;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.property.Property;
import oasis.economyx.types.asset.property.PropertyStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PropertyAbandonedEvent extends PropertyEvent {
    public PropertyAbandonedEvent(@NonNull PropertyStack property, @NonNull Actor holder) {
        super(property);
        this.holder = holder;
    }

    @NonNull
    private final Actor holder;

    @NonNull
    public Actor getHolder() {
        return holder;
    }
}
