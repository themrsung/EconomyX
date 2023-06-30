package oasis.economyx.interfaces.vaulting;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.services.VaultKeeper;
import oasis.economyx.types.asset.commodity.CommodityStack;
import org.bukkit.Location;
import org.bukkit.Material;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * An interface for a vault block.
 * There is no default implementation for vaults.
 */
public interface VaultBlock {
    /**
     * Do not change this after deployment.
     */
    Material VAULT_ITEM = Material.BARREL;

    /**
     * Gets the unique ID of this vault block.
     * @return Unique ID
     */
    @NonNull
    UUID getUniqueId();

    /**
     * Gets the vault keeper responsible for handling this vault.
     * When vault keeper is null, it means that this vault is not being managed.
     * Original design is to not allow deposits into unmanaged vaults, but still allow withdrawals.
     * However, you can change this to suit your needs. See VaultOpenedListener.
     *
     * @return Vault keeper
     */
    @Nullable
    VaultKeeper getKeeper();

    /**
     * Gets the client of this vault block.
     * @return Client
     */
    @NonNull
    Actor getClient();

    /**
     * Gets all items in this vault.
     * @return copied list of items
     */
    @NonNull
    List<CommodityStack> getItems();

    /**
     * Adds an item to the vault.
     * @param item Item to add
     */
    void addItem(@NonNull CommodityStack item);

    /**
     * Removes an item from the vault.
     * @param item Item to remove
     */
    void removeItem(@NonNull CommodityStack item);

    /**
     * Checks if there is enough space for this item.
     * @param item Item to check for
     * @return Whether there is enough empty space
     */
    boolean canFit(@NonNull CommodityStack item);

    /**
     * Checks if this vault contains the given item. (and its quantity)
     * @param item Item to check for
     * @return Whether this vault contains the item
     */
    boolean contains(@NonNull CommodityStack item);

    /**
     * Gets the maximum amount of stacks this vault allows.
     * @return Maximum size
     */
    @NonNegative
    long getMaxSize();

    /**
     * Gets the maximum amount of items per stack this vault allows.
     * @return Maximum stack size
     */
    @NonNegative
    long getMaxStackSize();

    /**
     * Gets the location this vault is located at.
     * @return Location
     */
    @NonNull
    Location getLocation();

    /**
     * Handles vault destruction.
     * Called when the physical block is destroyed.
     */
    void onDestroyed();
}
