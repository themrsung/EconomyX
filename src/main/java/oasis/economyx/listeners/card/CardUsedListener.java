package oasis.economyx.listeners.card;

import oasis.economyx.EconomyX;
import oasis.economyx.events.card.CardUsedEvent;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardUsedListener extends EconomyListener {
    public CardUsedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCardUsed(CardUsedEvent e) {
        if (e.isCancelled()) return;

        Card card = e.getCard();
        CashStack amount = e.getAmount();
        CardAcceptor seller = e.getSeller();

        card.onUsed(seller, amount);
    }
}
