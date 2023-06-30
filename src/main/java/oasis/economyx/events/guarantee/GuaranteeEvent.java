package oasis.economyx.events.guarantee;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Credible;
import oasis.economyx.interfaces.guarantee.Guarantee;
import oasis.economyx.types.asset.AssetStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

public abstract class GuaranteeEvent extends EconomyEvent {
    public GuaranteeEvent(@NonNull Guarantee guarantee) {
        this.guarantee = guarantee;
    }

    @NonNull
    private final Guarantee guarantee;

    @NonNull
    public Guarantee getGuarantee() {
        return guarantee;
    }

    @NonNull
    public Credible getGuarantor() {
        return getGuarantee().getGuarantor();
    }

    @NonNull
    public Actor getWarrantee() {
        return getGuarantee().getWarrantee();
    }

    @NonNull
    public AssetStack getLimit() {
        return getGuarantee().getLimit();
    }

    @Nullable
    public DateTime getExpiry() {
        return getGuarantee().getExpiry();
    }
}
