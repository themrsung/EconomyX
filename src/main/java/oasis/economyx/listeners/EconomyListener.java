package oasis.economyx.listeners;

import oasis.economyx.EconomyX;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A base class for EconomyX listeners
 */
public abstract class EconomyListener implements Listener {
    public EconomyListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        this.EX = EX;
        this.state = state;
    }

    @NonNull
    private final EconomyX EX;

    @NonNull
    private final EconomyState state;

    @NonNull
    protected EconomyX getEX() {
        return EX;
    }

    protected EconomyState getState() {
        return state;
    }
}
