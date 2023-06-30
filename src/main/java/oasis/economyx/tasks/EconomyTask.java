package oasis.economyx.tasks;

import oasis.economyx.EconomyX;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A base class for EconomyX tasks. (Runnables that require game state access)
 */
public abstract class EconomyTask implements Runnable {
    public EconomyTask(@NonNull EconomyX EX) {
        this.EX = EX;
    }

    @NonNull
    private final EconomyX EX;

    @NonNull
    protected EconomyX getEX() {
        return EX;
    }

    @NonNull
    protected EconomyState getState() {
        return getEX().getState();
    }

    public abstract int getDelay();

    public abstract int getInterval();
}
