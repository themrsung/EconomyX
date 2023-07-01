package oasis.economyx.events.terminal;

import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.terminal.CardTerminal;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents the creation of a card terminal.
 */
public final class CardTerminalCreatedEvent extends CardTerminalEvent {
    public CardTerminalCreatedEvent(@NonNull CardTerminal terminal, @NonNull CardAcceptor creator) {
        super(terminal);
        this.creator = creator;
    }

    @NonNull
    private final CardAcceptor creator;

    @NonNull
    public CardAcceptor getCreator() {
        return creator;
    }
}
