package oasis.economyx.events.stock;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class StockEvent extends EconomyEvent {
    public StockEvent(@NonNull Shared stockIssuer) {
        this.stockIssuer = stockIssuer;
    }

    @NonNull
    private final Shared stockIssuer;

    @NonNull
    public Shared getStockIssuer() {
        return stockIssuer;
    }
}
