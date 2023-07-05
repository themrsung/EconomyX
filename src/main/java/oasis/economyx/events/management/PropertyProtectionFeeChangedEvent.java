package oasis.economyx.events.management;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PropertyProtectionFeeChangedEvent extends EconomyEvent {
    public PropertyProtectionFeeChangedEvent(@NonNull PropertyProtector protector, @NonNull CashStack fee) {
        this.protector = protector;
        this.fee = fee;
    }

    @NonNull
    private final PropertyProtector protector;

    @NonNull
    private final CashStack fee;

    @NonNull
    public PropertyProtector getProtector() {
        return protector;
    }

    @NonNull
    public CashStack getFee() {
        return fee;
    }
}
