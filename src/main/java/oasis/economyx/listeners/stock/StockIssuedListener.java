package oasis.economyx.listeners.stock;

import oasis.economyx.EconomyX;
import oasis.economyx.events.stock.StockIssuedEvent;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class StockIssuedListener extends EconomyListener {
    public StockIssuedListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onStockIssued(StockIssuedEvent e) {
        if (e.isCancelled()) return;

        Shared issuer = e.getStockIssuer();
        long shares = e.getIssuedShares();

        issuer.addShareCount(shares);
        issuer.getAssets().add(new StockStack(
                new Stock(issuer.getStockId()),
                shares
        ));
    }
}
