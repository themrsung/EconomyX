package oasis.economyx.events.card;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.card.Card;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class CardEvent extends EconomyEvent {
    public CardEvent(@NonNull Card card) {
        this.card = card;
    }

    @NonNull
    private final Card card;

    @NonNull
    public Card getCard() {
        return card;
    }
}
