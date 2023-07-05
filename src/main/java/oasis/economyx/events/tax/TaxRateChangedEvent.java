package oasis.economyx.events.tax;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class TaxRateChangedEvent extends EconomyEvent {
    public TaxRateChangedEvent(@NonNull Sovereign sovereign, @NonNegative float rate, @NonNull TaxType type) {
        this.sovereign = sovereign;
        this.rate = rate;
        this.type = type;
    }

    @NonNull
    private final Sovereign sovereign;

    @NonNegative
    private final float rate;

    @NonNull
    private final TaxType type;

    @NonNull
    public Sovereign getSovereign() {
        return sovereign;
    }

    @NonNegative
    public float getRate() {
        return rate;
    }

    @NonNull
    public TaxType getType() {
        return type;
    }
}
