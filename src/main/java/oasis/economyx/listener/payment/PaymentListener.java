package oasis.economyx.listener.payment;

import oasis.economyx.EconomyX;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listener.EconomyListener;
import oasis.economyx.types.asset.AssetStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PaymentListener extends EconomyListener {
    public PaymentListener(@NonNull EconomyX EX) {
        super(EX);
    }
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPayment(PaymentEvent event) {
        if (event.isCancelled()) return;

        final Actor sender = event.getSender();
        final Actor recipient = event.getRecipient();
        final AssetStack asset = event.getAsset();

        if (!sender.getPayableAssets(getState()).contains(asset)) return;

        sender.getAssets().remove(asset);
        recipient.getAssets().add(asset);
    }
}
