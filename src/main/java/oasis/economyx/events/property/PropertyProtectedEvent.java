package oasis.economyx.events.property;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.property.PropertyStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Called when a property is protected.
 */
public final class PropertyProtectedEvent extends PropertyEvent {
    public PropertyProtectedEvent(@NonNull PropertyStack property, @NonNull Actor owner) {
        super(property);
        this.owner = owner;
    }

    @NonNull
    private final Actor owner;

    /**
     * Gets the owner of this property.
     *
     * @return Owner
     */
    @NonNull
    public Actor getOwner() {
        return owner;
    }
}
