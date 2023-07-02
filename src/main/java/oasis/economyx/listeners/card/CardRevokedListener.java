package oasis.economyx.listeners.card;

import oasis.economyx.EconomyX;
import oasis.economyx.events.card.CardRevokedEvent;
import oasis.economyx.interfaces.actor.types.finance.CardIssuer;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardRevokedListener extends EconomyListener {
    public CardRevokedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCardRevoked(CardRevokedEvent e) {
        if (e.isCancelled()) return;

        CardIssuer issuer = e.getCard().getIssuer();
        Card card = e.getCard();

        issuer.cancelCard(card);
    }
}
