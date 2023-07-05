package oasis.economyx.events.currency;

import oasis.economyx.interfaces.actor.types.institutional.CurrencyIssuer;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CurrencyIssuedEvent extends CurrencyEvent {
    public CurrencyIssuedEvent(@NonNull Cash currency, @NonNull CurrencyIssuer issuer, @NonNegative long amount) {
        super(currency);
        this.issuer = issuer;
        this.amount = amount;
    }

    @NonNull
    private final CurrencyIssuer issuer;

    @NonNegative
    private final long amount;

    @NonNull
    public CurrencyIssuer getIssuer() {
        return issuer;
    }

    @NonNegative
    public long getAmount() {
        return amount;
    }
}
