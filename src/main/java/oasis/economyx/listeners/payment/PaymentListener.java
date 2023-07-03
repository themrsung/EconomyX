package oasis.economyx.listeners.payment;

import oasis.economyx.EconomyX;
import oasis.economyx.events.message.MessageSentEvent;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.guarantee.Guarantee;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PaymentListener extends EconomyListener {
    private static final String PAYMENT_CANCELLED_EXTERNALLY = ChatColor.RED + "거래가 서버에 의해 취소되었습니다.";
    private static final String PAYMENT_CANCELLED_INSUFFICIENT_ASSETS = ChatColor.RED + "잔고 부족으로 거래가 취소되었습니다.";
    private static String PAYMENT_SENT(@NonNull PaymentEvent event, @NonNull EconomyState state) {
        return "["
                + (event.getSender().getName() != null ? event.getSender().getName() : "알 수 없음")
                + " -> "
                + (event.getRecipient().getName() != null ? event.getRecipient().getName() : "알 수 없음")
                + "] "
                + event.getAsset().format(state);
    }

    private static String PAYMENT_RECEIVED(@NonNull PaymentEvent event,@NonNull EconomyState state) {
        return "["
                + (event.getSender().getName() != null ? event.getSender().getName() : "알 수 없음")
                + " -> "
                + (event.getRecipient().getName() != null ? event.getRecipient().getName() : "알 수 없음")
                + "] "
                + event.getAsset().format(state);
    }

    public PaymentListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPayment(PaymentEvent event) {
        if (event.isCancelled()) {
            Bukkit.getPluginManager().callEvent(new MessageSentEvent(new Message(
                    null,
                    event.getSender(),
                    PAYMENT_CANCELLED_EXTERNALLY
            )));
            return;
        }

        final Actor sender = event.getSender();
        final Actor recipient = event.getRecipient();
        final AssetStack asset = event.getAsset();

        if (!sender.getPayableAssets(getState()).contains(asset)){
            // Sender is illiquid. Search for guarantees.

            boolean guaranteed = false;

            for (Guarantee guarantee : getState().getGuarantees()) {
                if (guarantee.getWarrantee().equals(sender)) {
                    // Guarantee found. Check for limit and asset type.
                    guaranteed = guarantee.getLimit().getAsset().equals(asset.getAsset()) && guarantee.getLimit().getQuantity() >= asset.getQuantity();
                    if (guaranteed) {
                        guarantee.onUsed(asset);
                        break;
                    }
                }
            }

            if (!guaranteed) {
                event.setCancelled(true);
                return;
            }
        }

        if (event.isCancelled()) {
            Bukkit.getPluginManager().callEvent(new MessageSentEvent(new Message(
                    null,
                    event.getSender(),
                    PAYMENT_CANCELLED_INSUFFICIENT_ASSETS
            )));
            return;
        }

        final AssetStack copy = asset.copy();

        sender.getAssets().remove(copy);
        recipient.getAssets().add(copy);

        Bukkit.getPluginManager().callEvent(new MessageSentEvent(new Message(
                null,
                sender,
                PAYMENT_SENT(event, getState())
        )));

        Bukkit.getPluginManager().callEvent(new MessageSentEvent(new Message(
                null,
                recipient,
                PAYMENT_RECEIVED(event, getState())
        )));
    }
}
