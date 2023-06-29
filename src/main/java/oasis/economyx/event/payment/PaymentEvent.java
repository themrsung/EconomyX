package oasis.economyx.event.payment;

import oasis.economyx.actor.Actor;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.event.EconomyEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.event.Cause;

/**
 * A payment event denotes the one-way flow of an asset stack from one holder to another
 */
public final class PaymentEvent extends EconomyEvent {
    public PaymentEvent(@NonNull Actor sender, @NonNull Actor recipient, @NonNull AssetStack asset, Cause cause, boolean checkSolvency) {
        super(cause);

        this.sender = sender;
        this.recipient = recipient;
        this.asset = asset;
        this.checkSolvency = checkSolvency;
    }

    @NonNull
    private final Actor sender;
    @NonNull
    private final Actor recipient;
    @NonNull
    private final AssetStack asset;
    private final boolean checkSolvency;

    @NonNull
    public Actor getSender() {
        return sender;
    }

    @NonNull
    public Actor getRecipient() {
        return recipient;
    }

    @NonNull
    public AssetStack getAsset() {
        return asset;
    }

    public boolean checkSolvency() {
        return checkSolvency;
    }
}
