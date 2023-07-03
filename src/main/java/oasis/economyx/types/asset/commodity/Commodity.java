package oasis.economyx.types.asset.commodity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.errorprone.annotations.DoNotCall;
import oasis.economyx.types.asset.Asset;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A commodity represents a deliverable item
 * Every item type has an equal unique ID
 */
public final class Commodity implements Asset {
    public Commodity() {
        this.itemType = Material.AIR;
    }

    public Commodity(@NonNull Material itemType) {
        this.itemType = itemType;
    }

    public Commodity(Commodity other) {
        this.itemType = other.itemType;
    }

    @NonNull
    @JsonProperty
    private final Material itemType;

    /**
     * Gets the item type of this commodity
     *
     * @return Type of item
     */
    @NonNull
    @JsonIgnore
    public Material getItemType() {
        return itemType;
    }

    /**
     * Generates a consistent unique ID for every single in-game item
     *
     * @return Unique ID
     */
    @Override
    @NonNull
    @JsonIgnore
    public UUID getUniqueId() {
        return UUID.nameUUIDFromBytes(getItemType().toString().getBytes());
    }

    @Override
    public @NonNull String getName() {
        return itemType.toString();
    }

    @Override
    @DoNotCall
    public void setName(@NonNull String name) {

    }

    @JsonProperty
    private final Type type = Type.COMMODITY;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull Commodity copy() {
        return new Commodity(this);
    }
}
