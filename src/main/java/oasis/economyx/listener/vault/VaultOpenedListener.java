package oasis.economyx.listener.vault;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.listener.EconomyListener;
import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.event.item.inventory.container.InteractContainerEvent;
import org.spongepowered.api.world.server.ServerLocation;

public final class VaultOpenedListener extends EconomyListener<InteractContainerEvent> {
    public VaultOpenedListener(EconomyX EX) {
        super(EX);
    }

    @Override
    public void handle(InteractContainerEvent event) throws Exception {
        ServerLocation location = null; // FIXME Get the clicked block location

        if(location == null) throw new RuntimeException();

        // Ignore event.isCancelled(), as vaults should work regardless of their protection status

        Player player = event.container().viewer();

        for (VaultBlock vb : getState().getVaultBlocks()) {
            if (vb.getLocation().equals(location)) {
                // Vault found

                vb.onOpenAttempted(player);
                return;
            }
        }
    }
}
