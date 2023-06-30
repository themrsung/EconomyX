package oasis.economyx.tasks.payment;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.tasks.EconomyTask;

public final class RegularPaymentTask extends EconomyTask {
    public RegularPaymentTask(EconomyX EX) {
        super(EX);
    }

    @Override
    public void run() {

        for (Banker b : getState().getBankers()) {
            b.payInterest();
        }

        for (Employer e : getState().getEmployers()) {
            e.paySalaries();
        }

        for (Representable r : getState().getRepresentables()) {
            r.payRepresentative();
        }
    }

    @Override
    public int getDelay() {
        return 20;
    }

    @Override
    public int getInterval() {
        return 60 * 60 * 20;
    }
}
