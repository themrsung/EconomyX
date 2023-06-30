package oasis.economyx.tasks.server;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.tasks.EconomyTask;

public final class AutoSaveTask extends EconomyTask {
    public AutoSaveTask(EconomyX EX) {
        super(EX);
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
