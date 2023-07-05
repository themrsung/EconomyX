package oasis.economyx.events.tax;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class TaxedEvent extends EconomyEvent {
    public TaxedEvent(@NonNull Actor taxPayer, @NonNull Sovereign taxCollector, @NonNull AssetStack tax, @NonNull TaxType type) {
        this.taxPayer = taxPayer;
        this.taxCollector = taxCollector;
        this.tax = tax;
        this.type = type;
    }

    @NonNull
    private final Actor taxPayer;

    @NonNull
    private final Sovereign taxCollector;

    @NonNull
    private final AssetStack tax;

    @NonNull
    private final TaxType type;

    @NonNull
    public Actor getTaxPayer() {
        return taxPayer;
    }

    @NonNull
    public Sovereign getTaxCollector() {
        return taxCollector;
    }

    @NonNull
    public AssetStack getTax() {
        return tax;
    }

    @NonNull
    public TaxType getType() {
        return type;
    }
}
