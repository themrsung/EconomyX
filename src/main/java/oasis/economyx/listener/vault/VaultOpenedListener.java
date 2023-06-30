package oasis.economyx.listener.vault;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.listener.EconomyListener;
import org.bukkit.Location;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerInteractEvent;

public final class VaultOpenedListener extends EconomyListener {
    public VaultOpenedListener(EconomyX EX) {
        super(EX);
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent e) throws IllegalArgumentException {
        if (e.getClickedBlock() == null) return;

        final Block block = e.getClickedBlock();

        if (block.getType() != VaultBlock.VAULT_ITEM) return;

        final Location location = block.getLocation();

        for (VaultBlock vb : getState().getVaultBlocks()) {
            if (vb.getLocation().equals(location)) {
                // Vault found
                final Player player = e.getPlayer();
                final Person person = getState().getPerson(player.getUniqueId());

                if (!(vb.getClient().equals(person))) return;

                // TODO open vault for player
            }
        }


    }
}
