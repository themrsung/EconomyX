package oasis.economyx.listener.vault;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.services.VaultKeeper;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.listener.EconomyListener;
import org.spongepowered.api.event.item.inventory.container.InteractContainerEvent;

public final class VaultOpenedListener extends EconomyListener<InteractContainerEvent> {
    public VaultOpenedListener(EconomyX EX) {
        super(EX);
    }

    @Override
    public void handle(InteractContainerEvent event) throws Exception {
        for (VaultBlock b : getState().getVaultBlocks()) {
            if (b.getLocation().equals(event.container().))
        }
    }
}
