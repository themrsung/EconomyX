package oasis.economyx.listener;

import oasis.economyx.EconomyX;
import oasis.economyx.events.EconomyEvent;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.event.EventListener;

/**
 * A base class for EconomyX listeners
 */
public abstract class EconomyListener<E extends EconomyEvent> implements EventListener<E> {
    public EconomyListener(@NonNull EconomyX EX) {
        this.EX = EX;
    }

    @NonNull
    private final EconomyX EX;

    @NonNull
    protected EconomyX getEX() {
        return EX;
    }

    protected EconomyState getState() {
        return getEX().getState();
    }
}
