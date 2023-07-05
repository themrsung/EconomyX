package oasis.economyx.tasks.trading;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.trading.Exchange;
import oasis.economyx.interfaces.trading.market.Marketplace;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class MarketTickTask extends EconomyTask {
    public MarketTickTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void run() {
        for (Exchange h : getState().getExchanges()) {
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
        return 1;
    }
}
