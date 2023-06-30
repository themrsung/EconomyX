package oasis.economyx.events.stock;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class StockSplitEvent extends EconomyEvent {
    /**
     * Splits a stock (does not trigger a transaction; simply multiplies the share count)
     *
     * @param issuer         Issuer of the stock
     * @param sharesPerShare Shares to create per 1 existing share
     */
    public StockSplitEvent(@NonNull Shared issuer, @NonNegative long sharesPerShare) {
        this.issuer = issuer;
        this.sharesPerShare = sharesPerShare;
    }

    @NonNull
    private final Shared issuer;

    @NonNegative
    private final long sharesPerShare;

    @NonNull
    public Shared getIssuer() {
        return issuer;
    }

    @NonNegative
    public long getSharesPerShare() {
        return sharesPerShare;
    }
}
