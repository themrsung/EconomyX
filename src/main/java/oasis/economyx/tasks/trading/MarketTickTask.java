package oasis.economyx.tasks.trading;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.trading.MarketHost;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.tasks.EconomyTask;

public final class MarketTickTask extends EconomyTask {
    public MarketTickTask(EconomyX EX) {
        super(EX);
    }

    @Override
    public void run() {
        for (MarketHost h : getState().getMarketHosts()) {
            for (Marketplace m : h.getMarkets()) {
                m.processOrders(h);
            }
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