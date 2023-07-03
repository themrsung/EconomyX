package oasis.economyx.events.vaulting;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class VaultEvent extends EconomyEvent {
    public VaultEvent(@NonNull VaultBlock vault) {
        this.vault = vault;
    }

    @NonNull
    private final VaultBlock vault;

    @NonNull
    public VaultBlock getVault() {
        return vault;
    }
}
