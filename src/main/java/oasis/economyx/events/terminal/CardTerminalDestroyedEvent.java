package oasis.economyx.events.terminal;

import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.terminal.CardTerminal;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class CardTerminalDestroyedEvent extends CardTerminalEvent {
    public CardTerminalDestroyedEvent(@NonNull CardTerminal terminal, @Nullable Person destroyer) {
        super(terminal);
        this.destroyer = destroyer;
    }

    @Nullable
    private final Person destroyer;

    @Nullable
    public Person getDestroyer() {
        return destroyer;
    }
}
