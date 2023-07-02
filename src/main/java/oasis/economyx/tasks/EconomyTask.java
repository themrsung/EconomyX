package oasis.economyx.tasks;

import oasis.economyx.EconomyX;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A base class for EconomyX tasks. (Runnables that require game state access)
 */
public abstract class EconomyTask implements Runnable {
    public EconomyTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
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

    @NonNull
    protected EconomyState getState() {
        return state;
    }

    public abstract int getDelay();

    public abstract int getInterval();
}
