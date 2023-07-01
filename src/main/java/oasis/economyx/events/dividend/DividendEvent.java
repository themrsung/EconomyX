package oasis.economyx.events.dividend;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class DividendEvent extends EconomyEvent {
    /**
     * Pays dividends to a shared actor's shareholders.
     *
     * @param payer            Shared actor to pay dividends
     * @param dividendPerShare Dividend per share (can be any asset)
     */
    public DividendEvent(@NonNull Shared payer, @NonNull AssetStack dividendPerShare) {
        this.payer = payer;
        this.dividendPerShare = dividendPerShare;
    }

    @NonNull
    private final Shared payer;

    @NonNull
    private final AssetStack dividendPerShare;

    @NonNull
    public Shared getPayer() {
        return payer;
    }

    @NonNull
    public AssetStack getDividendPerShare() {
        return dividendPerShare;
    }
}
