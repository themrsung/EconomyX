package oasis.economyx.tasks.server;

import oasis.economyx.EconomyX;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AutoSaveTask extends EconomyTask {
    public AutoSaveTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void run() {
        getState().save();
    }

    @Override
    public int getDelay() {
        return 60 * 20;
    }

    @Override
    public int getInterval() {
        return 60 * 20;
    }
}
