package oasis.economyx.listeners.stock;

import oasis.economyx.EconomyX;
import oasis.economyx.events.stock.StockSplitEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class StockSplitListener extends EconomyListener {
    public StockSplitListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockSplit(StockSplitEvent e) {
        if (e.isCancelled()) return;

        final Shared issuer = e.getStockIssuer();
        final long shares = e.getSharesPerShare();

        for (Actor a : issuer.getShareholders(getState())) {
            final AssetStack as = a.getAssets().get(new Stock(issuer.getStockId()));
            if (as instanceof StockStack ss) {
                ss.setQuantity(ss.getQuantity() * (1 + shares));
            }
        }
    }
}
