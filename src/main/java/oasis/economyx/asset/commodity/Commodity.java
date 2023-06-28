package oasis.economyx.asset.commodity;

import oasis.economyx.asset.Asset;
import oasis.economyx.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.spongepowered.api.item.ItemType;
import org.spongepowered.api.item.ItemTypes;

import java.util.UUID;

/**
 * A commodity represents a deliverable item
 * Every item type has an equal unique ID
 */
public final class Commodity implements Asset {
    public Commodity() {
        this.itemType = ItemTypes.AIR.get();
    }

    public Commodity(@NonNull ItemType itemType) {
        this.itemType = itemType;
    }

    public Commodity(Commodity other) {
        this.itemType = other.itemType;
    }

    @NonNull
    private final ItemType itemType;

    /**
     * Gets the item type of this commodity
     * @return Type of item
     */
    @NonNull
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * Generates a consistent unique ID for every single in-game item
     * @return Unique ID
     */
    @Override
    @NonNull
    public UUID getUniqueId() {
        return UUID.nameUUIDFromBytes(getItemType().toString().getBytes());
    }

    @Override
    public @NonNull AssetType getType() {
        return AssetType.COMMODITY;
    }
}
