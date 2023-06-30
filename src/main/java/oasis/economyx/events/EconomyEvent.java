package oasis.economyx.events;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * A base class for EconomyX events.
 * All EconomyX events are cancellable, have a unique ID, and time.
 */
public abstract class EconomyEvent extends Event implements Cancellable {
    public EconomyEvent() {
        this.uniqueId = UUID.randomUUID();
        this.time = new DateTime();
        this.cancelled = false;
    }

    @NonNull
    private final UUID uniqueId;

    @NonNull
    private final DateTime time;

    private boolean cancelled;

    @NonNull
    public UUID getUniqueId() {
        return uniqueId;
    }

    @NonNull
    public DateTime getTime() {
        return time;
    }

    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    @Override
    public void setCancelled(boolean cancel) {
        this.cancelled = cancel;
    }

    private static final HandlerList handlers = new HandlerList();

    @NonNull
    public HandlerList getHandlers() {
        return handlers;
    }

    public static HandlerList getHandlerList() {
        return handlers;
    }
}
