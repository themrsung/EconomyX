package oasis.economyx.events.property;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.types.asset.property.PropertyStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class PropertyEvent extends EconomyEvent {
    public PropertyEvent(@NonNull PropertyStack property) {
        this.property = property;
    }

    @NonNull
    private final PropertyStack property;

    @NonNull
    public PropertyStack getProperty() {
        return property;
    }
}
