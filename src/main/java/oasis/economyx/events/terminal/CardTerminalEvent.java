package oasis.economyx.events.terminal;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.terminal.CardTerminal;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Handles the events of a card terminal.
 */
public abstract class CardTerminalEvent extends EconomyEvent {
    public CardTerminalEvent(@NonNull CardTerminal terminal) {
        this.terminal = terminal;
    }

    @NonNull
    private final CardTerminal terminal;

    @NonNull
    public CardTerminal getTerminal() {
        return terminal;
    }
}
