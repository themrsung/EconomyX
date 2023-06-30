package oasis.economyx.listeners;

import oasis.economyx.EconomyX;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.Listener;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A base class for EconomyX listeners
 */
public abstract class EconomyListener implements Listener {
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
