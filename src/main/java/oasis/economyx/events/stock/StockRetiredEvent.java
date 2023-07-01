package oasis.economyx.events.stock;

import oasis.economyx.interfaces.actor.types.ownership.Shared;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class StockRetiredEvent extends StockEvent {
    public StockRetiredEvent(@NonNull Shared stockIssuer, @NonNegative long retiredShares) {
        super(stockIssuer);
        this.retiredShares = retiredShares;
    }

    @NonNegative
    private final long retiredShares;

    @NonNegative
    public long getRetiredShares() {
        return retiredShares;
    }
}
