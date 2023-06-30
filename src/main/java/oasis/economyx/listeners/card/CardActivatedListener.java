package oasis.economyx.listeners.card;

import oasis.economyx.EconomyX;
import oasis.economyx.events.card.CardActivatedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listeners.EconomyListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardActivatedListener extends EconomyListener {
    public CardActivatedListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCardActivated(CardActivatedEvent e) {
        if (e.isCancelled()) return;

        Actor activator = e.getActivator();
        Actor holder = e.getCard().getHolder();

        if (!(activator.equals(holder) || e.forceActivate())) return;

        e.getCard().setActive(true);
    }
}
