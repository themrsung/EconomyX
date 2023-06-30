package oasis.economyx.listener.payment;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listener.EconomyListener;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.events.payment.PaymentEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PaymentListener extends EconomyListener<PaymentEvent> {
    public PaymentListener(@NonNull EconomyX EX) {
        super(EX);
    }
    @Override
    public void handle(PaymentEvent event) {
        if (event.isCancelled()) return;

        final Actor sender = event.getSender();
        final Actor recipient = event.getRecipient();
        final AssetStack asset = event.getAsset();

        if (!sender.getPayableAssets(getState()).contains(asset)) return;

        sender.getAssets().remove(asset);
        recipient.getAssets().add(asset);
    }
}
