package oasis.economyx.events.card;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.card.Card;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardActivatedEvent extends CardEvent {
    public CardActivatedEvent(@NonNull Card card, @NonNull Actor activator, boolean forceActivate) {
        super(card);
        this.activator = activator;
        this.forceActivate = forceActivate;
    }

    @NonNull
    private final Actor activator;

    private boolean forceActivate;

    @NonNull
    public Actor getActivator() {
        return activator;
    }

    public boolean forceActivate() {
        return forceActivate;
    }

    public void setForceActivate(boolean forceActivate) {
        this.forceActivate = forceActivate;
    }
}
