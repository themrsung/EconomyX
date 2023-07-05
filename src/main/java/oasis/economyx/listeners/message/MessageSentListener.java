package oasis.economyx.listeners.message;

import oasis.economyx.EconomyX;
import oasis.economyx.events.message.MessageSentEvent;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class MessageSentListener extends EconomyListener {
    public MessageSentListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void TEMP_BROKERAGE_MESSAGE_CANCELLER(MessageSentEvent e) {
        if (e.getMessage().getRecipient().getUniqueId().equals(EconomyState.TEMP_BROKER.getUniqueId())) {
            e.setCancelled(true);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onMessageSent(MessageSentEvent e) {
        if (e.isCancelled()) return;

        getState().addMessage(e.getMessage());
    }
}
