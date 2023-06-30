package oasis.economyx.events.card;

import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.interfaces.card.CardTerminal;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardUsedEvent extends CardEvent {
    public CardUsedEvent(@NonNull Card card, @NonNull Player user, @NonNull CardTerminal terminal) {
        super(card);
        this.user = user;
        this.terminal = terminal;
    }

    @NonNull
    private final Player user;

    @NonNull
    private final CardTerminal terminal;

    @NonNull
    public Player getUser() {
        return user;
    }

    @NonNull
    public CardTerminal getTerminal() {
        return terminal;
    }

    @NonNull
    public CardAcceptor getSeller() {
        return getTerminal().getSeller();
    }

    @NonNull
    public CashStack getAmount() {
        return getTerminal().getPrice();
    }
}
