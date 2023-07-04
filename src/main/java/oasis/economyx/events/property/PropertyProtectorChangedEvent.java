package oasis.economyx.events.property;

import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.types.asset.property.PropertyStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class PropertyProtectorChangedEvent extends PropertyEvent {
    public PropertyProtectorChangedEvent(@NonNull PropertyStack property, @Nullable PropertyProtector protector) {
        super(property);
        this.protector = protector;
    }

    /**
     * The new protector after this event is processed.
     */
    @Nullable
    private final PropertyProtector protector;

    /**
     * Gets the new protector after this event is processed.
     *
     * @return New protector
     */
    @Nullable
    public PropertyProtector getProtector() {
        return protector;
    }
}
