package oasis.economyx.tasks.gaming;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.gaming.table.Table;
import oasis.economyx.tasks.EconomyTask;

public final class CasinoProgressTask extends EconomyTask {
    public CasinoProgressTask(EconomyX EX) {
        super(EX);
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
