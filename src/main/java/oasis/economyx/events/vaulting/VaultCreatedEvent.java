package oasis.economyx.events.vaulting;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.services.VaultKeeper;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Called when a vault has been created.
 */
public final class VaultCreatedEvent extends EconomyEvent {
    /**
     * Creates a new vault created event.
     *
     * @param keeper Keeper of this vault
     * @param vault  Physical block of this vault
     */
    public VaultCreatedEvent(@NonNull VaultKeeper keeper, @NonNull VaultBlock vault) {
        this.keeper = keeper;
        this.vault = vault;
    }

    @NonNull
    private final VaultKeeper keeper;

    @NonNull
    private final VaultBlock vault;

    @NonNull
    public VaultKeeper getKeeper() {
        return keeper;
    }

    @NonNull
    public VaultBlock getVault() {
        return vault;
    }
}
