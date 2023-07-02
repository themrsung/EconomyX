package oasis.economyx.tasks.expiry;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardExpiryTask extends EconomyTask {
    public CardExpiryTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void run() {
        for (Card c : getState().getCards()) {
            c.onExpired();
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
