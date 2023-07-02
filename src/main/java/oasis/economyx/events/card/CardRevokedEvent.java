package oasis.economyx.events.card;

import oasis.economyx.interfaces.card.Card;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Called when a card is revoked. (either by its issuer or its holder)
 */
public final class CardRevokedEvent extends CardEvent {
    public CardRevokedEvent(@NonNull Card card) {
        super(card);
    }
}
