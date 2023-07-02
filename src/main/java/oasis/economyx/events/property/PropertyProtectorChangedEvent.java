package oasis.economyx.events.property;

import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.types.asset.property.PropertyStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PropertyProtectorChangedEvent extends PropertyEvent {
    public PropertyProtectorChangedEvent(@NonNull PropertyStack property, @NonNull PropertyProtector protector) {
        super(property);
        this.protector = protector;
    }

    /**
     * The new protector after this event is processed.
     */
    @NonNull
    private final PropertyProtector protector;

    /**
     * Gets the new protector after this event is processed.
     *
     * @return New protector
     */
    @NonNull
    public PropertyProtector getProtector() {
        return protector;
    }
}
