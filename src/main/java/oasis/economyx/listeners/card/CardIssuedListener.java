package oasis.economyx.listeners.card;

import oasis.economyx.EconomyX;
import oasis.economyx.events.card.CardIssuedEvent;
import oasis.economyx.interfaces.actor.types.finance.CardIssuer;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardIssuedListener extends EconomyListener {
    public CardIssuedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCardIssued(CardIssuedEvent e) {
        if (e.isCancelled()) return;

        CardIssuer issuer = e.getCard().getIssuer();
        Player physicalIssuer = e.getPhysicalIssuer();

        physicalIssuer.getInventory().addItem(issuer.issueCard(e.getCard()));
    }
}
