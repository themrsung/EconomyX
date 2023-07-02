package oasis.economyx.listeners.terminal;

import oasis.economyx.EconomyX;
import oasis.economyx.events.card.CardUsedEvent;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.interfaces.terminal.CardTerminal;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Listens for clicks on card terminals by players.
 */
public final class CardTerminalClickedListener extends EconomyListener {
    public CardTerminalClickedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    /**
     * Calls {@link CardUsedEvent} if the interact event is a valid card use.
     *
     * @param e Event
     */
    @EventHandler(priority = EventPriority.MONITOR)
    public void onTerminalClicked(PlayerInteractEvent e) {
        if (e.getClickedBlock() == null) return;

        Block block = e.getClickedBlock();
        if (block.getType() != CardTerminal.TERMINAL_ITEM) return;

        for (CardTerminal ct : getState().getCardTerminals()) {
            if (ct.getAddress().toLocation().equals(block.getLocation())) {
                ItemStack item = e.getPlayer().getInventory().getItemInMainHand();
                if (item.getType() != Card.CARD_ITEM) return;

                if (item.getItemMeta() == null) return;
                List<String> lore = item.getItemMeta().getLore();

                if (lore == null) return;

                UUID cardId = UUID.fromString(lore.get(0));
                for (Card card : getState().getCards()) {
                    if (card.getUniqueId().equals(cardId)) {

                        Bukkit.getPluginManager().callEvent(new CardUsedEvent(
                                card,
                                e.getPlayer(),
                                ct
                        ));

                        return;
                    }
                }

                return;
            }
        }
    }
}
