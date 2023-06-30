package oasis.economyx.events.card;

import oasis.economyx.interfaces.card.Card;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardIssuedEvent extends CardEvent{
    public CardIssuedEvent(@NonNull Card card, @NonNull Player physicalIssuer) {
        super(card);
        this.physicalIssuer = physicalIssuer;
    }

    @NonNull
    private final Player physicalIssuer;

    @NonNull
    public Player getPhysicalIssuer() {
        return physicalIssuer;
    }
}
