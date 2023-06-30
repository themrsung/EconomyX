package oasis.economyx.events.guarantee;

import oasis.economyx.interfaces.guarantee.Guarantee;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class GuaranteeIssuedEvent extends GuaranteeEvent {
    public GuaranteeIssuedEvent(@NonNull Guarantee guarantee) {
        super(guarantee);
    }
}
