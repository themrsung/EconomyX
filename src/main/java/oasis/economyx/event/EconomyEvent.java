package oasis.economyx.event;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.impl.AbstractEvent;

/**
 * A base class for EconomyX events
 * All EconomyX events are cancellable
 */
public class EconomyEvent extends AbstractEvent implements Cancellable {

    private boolean cancelled;
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    @Override
    public Cause cause() {
        return null;
    }
}
