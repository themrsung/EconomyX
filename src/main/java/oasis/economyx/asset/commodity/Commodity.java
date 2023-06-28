package oasis.economyx.asset.commodity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty
    private final ItemType itemType;

    /**
     * Gets the item type of this commodity
     * @return Type of item
     */
    @NonNull
    @JsonIgnore
    public ItemType getItemType() {
        return itemType;
    }

    /**
     * Generates a consistent unique ID for every single in-game item
     * @return Unique ID
     */
    @Override
    @NonNull
    @JsonIgnore
    public UUID getUniqueId() {
        return UUID.nameUUIDFromBytes(getItemType().toString().getBytes());
    }

    @JsonProperty
    private final AssetType type = AssetType.COMMODITY;

    @Override
    @JsonIgnore
    public @NonNull AssetType getType() {
        return type;
    }
}
