package oasis.economyx.events.vaulting;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.services.VaultKeeper;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Called when a vault has been created.
 */
public final class VaultCreatedEvent extends VaultEvent {
    /**
     * Creates a new vault created event.
     *
     * @param vault  Physical block of this vault
     * @param keeper Keeper of this vault
     */
    public VaultCreatedEvent(@NonNull VaultBlock vault, @NonNull VaultKeeper keeper) {
        super(vault);
        this.keeper = keeper;
    }

    @NonNull
    private final VaultKeeper keeper;

    @NonNull
    public VaultKeeper getKeeper() {
        return keeper;
    }
}
