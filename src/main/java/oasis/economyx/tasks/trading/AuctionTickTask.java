package oasis.economyx.tasks.trading;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.trading.AuctionHouse;
import oasis.economyx.interfaces.trading.auction.Auctioneer;
import oasis.economyx.tasks.EconomyTask;

public final class AuctionTickTask extends EconomyTask {
    public AuctionTickTask(EconomyX EX) {
        super(EX);
    }

    @Override
    public void run() {
        for (AuctionHouse h : getState().getAuctionHosts()) {
            for (Auctioneer a : h.getAuctions()) {
                a.processBids(h);

                if (a.hasExpired()) a.onDeadlineReached(h);
            }
        }
    }

    @Override
    public int getDelay() {
        return 20;
    }

    @Override
    public int getInterval() {
        return 20;
    }
}
