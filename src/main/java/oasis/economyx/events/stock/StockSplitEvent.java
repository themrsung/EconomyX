package oasis.economyx.events.stock;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class StockSplitEvent extends StockEvent {
    /**
     * Splits a stock (does not trigger a transaction; simply multiplies the share count)
     *
     * @param issuer         Issuer of the stock
     * @param sharesPerShare Shares to create per 1 existing share
     */
    public StockSplitEvent(@NonNull Shared issuer, @NonNegative long sharesPerShare) {
        super(issuer);
        this.sharesPerShare = sharesPerShare;
    }
    @NonNegative
    private final long sharesPerShare;

    @NonNegative
    public long getSharesPerShare() {
        return sharesPerShare;
    }
}
