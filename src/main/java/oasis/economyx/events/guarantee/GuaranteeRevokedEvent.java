package oasis.economyx.events.guarantee;

import oasis.economyx.interfaces.guarantee.Guarantee;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class GuaranteeRevokedEvent extends GuaranteeEvent {
    public GuaranteeRevokedEvent(@NonNull Guarantee guarantee) {
        super(guarantee);
    }
}
