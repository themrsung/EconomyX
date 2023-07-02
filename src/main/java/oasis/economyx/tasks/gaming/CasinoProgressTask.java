package oasis.economyx.tasks.gaming;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.gaming.table.Table;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CasinoProgressTask extends EconomyTask {
    public CasinoProgressTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void run() {
        for (Table t : getState().getTables()) {
            t.progressGame();
        }
    }

    @Override
    public int getDelay() {
        return 20;
    }

    @Override
    public int getInterval() {
        return 2;
    }
}
