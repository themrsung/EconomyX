package oasis.economyx.events.payment;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.events.EconomyEvent;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.event.Cause;

/**
 * A payment events denotes the one-way flow of an asset stack from one holder to another
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
