package oasis.economyx.tasks.payment;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class RegularPaymentTask extends EconomyTask {
    public RegularPaymentTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
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
