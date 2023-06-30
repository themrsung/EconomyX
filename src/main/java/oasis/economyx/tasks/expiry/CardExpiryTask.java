package oasis.economyx.tasks.expiry;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.tasks.EconomyTask;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.Contract;

public final class CardExpiryTask extends EconomyTask {
    public CardExpiryTask(EconomyX EX) {
        super(EX);
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