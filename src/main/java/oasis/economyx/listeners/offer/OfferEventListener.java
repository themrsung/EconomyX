package oasis.economyx.listeners.offer;

import oasis.economyx.EconomyX;
import oasis.economyx.events.message.MessageSentEvent;
import oasis.economyx.events.offer.OfferAcceptedEvent;
import oasis.economyx.events.offer.OfferDeclinedEvent;
import oasis.economyx.events.offer.OfferSentEvent;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class OfferEventListener extends EconomyListener {
    private static final String OFFER_SENT = ChatColor.GREEN + "제안이 발송되었습니다.";
    private static final String OFFER_RECEIVED = ChatColor.GREEN + "제안이 수신되었습니다.";
    public static final String OFFER_ACCEPTED = ChatColor.GREEN + "제안을 수락했습니다.";
    public static final String OFFER_DECLINED = ChatColor.GREEN + "제안이 거절되었습니다.";

    public OfferEventListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOfferSent(OfferSentEvent e) {
        getState().addOffer(e.getOffer());
        Bukkit.getPluginManager().callEvent(new MessageSentEvent(new Message(
                null,
                e.getOffer().getSender(),
                OFFER_SENT
        )));
        Bukkit.getPluginManager().callEvent(new MessageSentEvent(new Message(
                null,
                e.getOffer().getRecipient(),
                OFFER_RECEIVED
        )));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOfferAccepted(OfferAcceptedEvent e) {
        e.getOffer().onAccepted();
        Bukkit.getPluginManager().callEvent(new MessageSentEvent(new Message(
                null,
                e.getOffer().getRecipient(),
                OFFER_ACCEPTED
        )));
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOfferDeclined(OfferDeclinedEvent e) {
        e.getOffer().onDeclined(getState());
        Bukkit.getPluginManager().callEvent(new MessageSentEvent(new Message(
                null,
                e.getOffer().getRecipient(),
                OFFER_DECLINED
        )));
    }
}
