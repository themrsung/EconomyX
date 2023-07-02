package oasis.economyx.listeners.stock;

import oasis.economyx.EconomyX;
import oasis.economyx.events.stock.StockRetiredEvent;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class StockRetiredListener extends EconomyListener {
    public StockRetiredListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockRetired(StockRetiredEvent e) {
        if (e.isCancelled()) return;

        Shared corp = e.getStockIssuer();
        long shares = e.getRetiredShares();

        try {
            corp.getAssets().remove(new StockStack(
                    new Stock(corp.getStockId()),
                    shares
            ));
        } catch (IllegalArgumentException error) {
            // Insufficient self-owned shares
        }

        corp.reduceShareCount(shares);
    }
}
