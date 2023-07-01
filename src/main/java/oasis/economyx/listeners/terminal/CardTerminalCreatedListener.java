package oasis.economyx.listeners.terminal;

import oasis.economyx.EconomyX;
import oasis.economyx.events.terminal.CardTerminalCreatedEvent;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.terminal.CardTerminal;
import oasis.economyx.listeners.EconomyListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardTerminalCreatedListener extends EconomyListener {
    public CardTerminalCreatedListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTerminalCreated(CardTerminalCreatedEvent e) {
        if (e.isCancelled()) return;

        CardAcceptor seller = e.getCreator();
        CardTerminal terminal = e.getTerminal();

        seller.addCardTerminal(terminal);
    }
}
