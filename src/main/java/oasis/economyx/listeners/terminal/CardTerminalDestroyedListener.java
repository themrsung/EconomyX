package oasis.economyx.listeners.terminal;

import oasis.economyx.EconomyX;
import oasis.economyx.events.terminal.CardTerminalDestroyedEvent;
import oasis.economyx.interfaces.terminal.CardTerminal;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CardTerminalDestroyedListener extends EconomyListener {
    public CardTerminalDestroyedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onTerminalDestroyed(CardTerminalDestroyedEvent e) {
        if (e.isCancelled()) return;

        CardTerminal terminal = e.getTerminal();
        terminal.getSeller().removeCardTerminal(terminal);
    }
}
