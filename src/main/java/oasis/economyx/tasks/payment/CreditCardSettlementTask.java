package oasis.economyx.tasks.payment;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.card.CreditCard;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CreditCardSettlementTask extends EconomyTask {
    public CreditCardSettlementTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
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
