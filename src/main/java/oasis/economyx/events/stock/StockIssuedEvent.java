package oasis.economyx.events.stock;

import oasis.economyx.interfaces.actor.types.ownership.Shared;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class StockIssuedEvent extends StockEvent {
    public StockIssuedEvent(@NonNull Shared stockIssuer, @NonNegative long issuedShares) {
        super(stockIssuer);
        this.issuedShares = issuedShares;
    }

    @NonNegative
    private final long issuedShares;

    @NonNegative
    public long getIssuedShares() {
        return issuedShares;
    }
}
