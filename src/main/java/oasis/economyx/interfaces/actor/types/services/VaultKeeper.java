package oasis.economyx.interfaces.actor.types.services;

import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * A vault keeper can host and manage vaults
 * <p>
 *     Vaults are a special in-game block which only permits the keeper and client to access it.
 *     This is intended to be used with a mod which creates this block for the plugin.
 *     Vault keepers will do nothing if no mod is provided.
 * </p>
 *
 */
public interface VaultKeeper extends Actor {
    /**
     * Gets all vaults this keeper is managing
     * @return A copied list of vaults
     */
    @NonNull
    List<VaultBlock> getVaults();

    /**
     * Assigns a vault to this vault keeper
     * A vault has to be created before being added
     *
     * @param vault Vault ot add
     */
    void addVault(@NonNull VaultBlock vault);

    /**
     * Unregisters a vault from this keeper
     * Note that this will not destroy the vault; It will simply unregister it from this keeper
     * @param vault Vault to remove
     */
    void removeVault(@NonNull VaultBlock vault);

    /**
     * Gets the hourly maintenance fee of this vault keeper
     * Every vault pays this once every hour regardless of its contents
     * @return Hourly fee
     */
    @NonNull
    CashStack getHourlyVaultFee();

    /**
     * Sets the hourly maintenance fee of this vault keeper
     * @param fee Hourly fee
     */
    void setHourlyVaultFee(@NonNull CashStack fee);

    /**
     * Collects fees from clients
     */
     default void collectHourlyVaultFee() {
        for (VaultBlock v : getVaults()) {
            Bukkit.getPluginManager().callEvent(new PaymentEvent(
                    v.getClient(),
                    this,
                    getHourlyVaultFee(),
                    PaymentEvent.Cause.VAULT_MAINTENANCE_FEE
            ));
        }
    }
}
