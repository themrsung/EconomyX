package oasis.economyx.types.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

/**
 * Represents an in-game location
 *
 * @param world World of this address
 * @param x     X coordinate
 * @param y     Y coordinate (height)
 * @param z     Z coordinate
 * @param yaw   Yaw (horizontal scalar)
 * @param pitch Pitch (vertical scalar)
 */
public record Address(
        @JsonProperty String world,
        @JsonProperty double x,
        @JsonProperty double y,
        @JsonProperty double z,
        @JsonProperty float yaw,
        @JsonProperty float pitch
) {
    /**
     * Converts this address into a location that Bukkit can understand.
     *
     * @return Location
     * @throws RuntimeException When the world is invalid
     */
    @JsonIgnore
    public Location toLocation() throws RuntimeException {
        World w = Bukkit.getWorld(world);
        if (w == null) throw new RuntimeException();

        return new Location(w, x, y, z, yaw, pitch);
    }

    /**
     * Converts a Bukkit location to an EconomyX address.
     *
     * @param location Bukkit location
     * @return Address
     * @throws IllegalArgumentException When the given location's world is invalid
     */
    @JsonIgnore
    public static Address fromLocation(Location location) throws IllegalArgumentException {
        World w = location.getWorld();
        if (w == null) throw new IllegalArgumentException();

        return new Address(
                location.getWorld().getName(),
                location.getX(),
                location.getY(),
                location.getZ(),
                location.getYaw(),
                location.getPitch()
        );
    }

}
