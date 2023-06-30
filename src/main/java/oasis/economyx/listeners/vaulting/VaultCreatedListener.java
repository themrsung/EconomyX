package oasis.economyx.listeners.vaulting;

import oasis.economyx.EconomyX;
import oasis.economyx.events.vaulting.VaultCreatedEvent;
import oasis.economyx.interfaces.actor.types.services.VaultKeeper;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.listeners.EconomyListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class VaultCreatedListener extends EconomyListener {
    public VaultCreatedListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVaultCreated(VaultCreatedEvent e) {
        if (e.isCancelled()) return;

        VaultKeeper keeper = e.getKeeper();
        VaultBlock block = e.getVault();

        keeper.addVault(block);
    }
}
