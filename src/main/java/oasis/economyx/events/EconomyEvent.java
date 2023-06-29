package oasis.economyx.events;

import org.spongepowered.api.event.Cancellable;
import org.spongepowered.api.event.Cause;
import org.spongepowered.api.event.filter.IsCancelled;
import org.spongepowered.api.event.impl.AbstractEvent;
import org.spongepowered.api.util.Tristate;

/**
 * A base class for EconomyX events
 * All EconomyX events are cancellable
 */
public abstract class EconomyEvent extends AbstractEvent implements Cancellable {
    public EconomyEvent(Cause cause) {
        this.cause = cause;
        this.cancelled = false;
    }

    private boolean cancelled;

    @Override
    @IsCancelled(Tristate.UNDEFINED)
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    private final Cause cause;

    @Override
    public Cause cause() {
        return cause;
    }
}
