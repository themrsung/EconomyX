package oasis.economyx.tasks.payment;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.card.CreditCard;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.tasks.EconomyTask;

public final class CreditCardSettlementTask extends EconomyTask {
    public CreditCardSettlementTask(EconomyX EX) {
        super(EX);
    }

    @Override
    public void run() {
        for (Card c : getState().getCards()) {
            if (c instanceof CreditCard cc) {
                cc.onSettled();
            }
        }
    }

    @Override
    public int getDelay() {
        return 20;
    }

    @Override
    public int getInterval() {
        return 12 * 60 * 60 * 20;
    }
}
