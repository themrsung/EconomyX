package oasis.economyx.events.currency;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class CurrencyEvent extends EconomyEvent {
    public CurrencyEvent(@NonNull Cash currency) {
        this.currency = currency;
    }

    @NonNull
    private final Cash currency;

    @NonNull
    public Cash getCurrency() {
        return currency;
    }
}
