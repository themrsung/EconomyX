package oasis.economyx.types.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Represents an area of land.
 *
 * @param pointA Point A
 * @param pointB Point B
 */
public record Area(
        @JsonProperty Address pointA,
        @JsonProperty Address pointB
) {
    /**
     * Gets the area of this land.
     *
     * @return Area
     * @throws RuntimeException When either of the points are invalid, or the world is different
     */
    @JsonIgnore
    public double area() throws RuntimeException {
        double diagonal = pointA.toLocation().distance(pointB.toLocation());
        double diagonalSquared = Math.pow(diagonal, 2);

        return diagonalSquared / 2;
    }

    /**
     * Gets the center of this land.
     * Yaw and pitch are not averaged, and are fixed values.
     *
     * @return Center of this area
     * @throws RuntimeException When the worlds of the two points are different
     */
    @JsonIgnore
    public Address center() throws RuntimeException {
        if (!Objects.equals(pointA.world(), pointB.world())) throw new RuntimeException();

        double xDelta = Math.abs(pointA.x() - pointB.x());
        double zDelta = Math.abs(pointA.z() - pointB.z());

        return new Address(
                pointA.world(),
                pointA.x() + xDelta,
                Math.max(pointA.y(), pointB.y()),
                pointA.z() + zDelta,
                0f,
                0f
        );
    }

    /**
     * Checks if this area contains a certain address.
     * Ignores the Y value. Rewrite this function to change its behavior.
     * If you have a better implementation, please send a PR. Credit yourself as well.
     *
     * @param address Address to check
     * @return Whether address is within the bounds of this area
     * @throws RuntimeException When the worlds are different
     */
    @JsonIgnore
    public boolean contains(@NonNull Address address) throws RuntimeException {
        if (!Objects.equals(pointA.world(), address.world()) || !Objects.equals(pointB.world(), address.world()))
            throw new RuntimeException();

        double x = address.x();
        double z = address.z();

        double x1 = pointA.x();
        double x2 = pointB.x();

        double minX = Math.min(x1, x2);
        double maxX = Math.max(x1, x2);

        double z1 = pointA.z();
        double z2 = pointB.z();

        double minZ = Math.min(z1, z2);
        double maxZ = Math.max(z1, z2);

        return x >= minX && x <= maxX && z >= minZ && z <= maxZ;
    }

    /**
     * Checks if this area contains another area.
     *
     * @param area Area to check
     * @return Whether given area is within the bounds of this area
     * @throws RuntimeException When the worlds are different
     */
    @JsonIgnore
    public boolean contains(@NonNull Area area) throws RuntimeException {
        if (!Objects.equals(world(), area.world())) throw new RuntimeException();

        List<Address> points = new ArrayList<>();
        points.add(area.pointA);
        points.add(area.pointB);
        points.add(new Address(area.world(), area.pointA.x(), 0d, area.pointB.z(), 0f, 0f));
        points.add(new Address(area.world(), area.pointB.x(), 0d, area.pointA.z(), 0f, 0f));

        for (Address point : points) {
            if (!contains(point)) return false;
        }

        return true;
    }

    @JsonIgnore
    public boolean overlaps(@NonNull Area area) throws RuntimeException {
        if (!Objects.equals(world(), area.world())) throw new RuntimeException();

        List<Address> points = new ArrayList<>();
        points.add(area.pointA);
        points.add(area.pointB);
        points.add(new Address(area.world(), area.pointA.x(), 0d, area.pointB.z(), 0f, 0f));
        points.add(new Address(area.world(), area.pointB.x(), 0d, area.pointA.z(), 0f, 0f));

        for (Address point : points) {
            if (contains(point)) return true;
        }

        return false;
    }

    /**
     * Gets the world of this area.
     *
     * @return World
     * @throws RuntimeException When the worlds of point A and B are different
     */
    @JsonIgnore
    public String world() throws RuntimeException {
        if (!Objects.equals(pointA.world(), pointB.world())) throw new RuntimeException();
        return pointA.world();
    }

    @JsonIgnore
    public String format() {
        return "[지역: " +
                "A지점: [" + pointA.format() +
                "] B지점: [" + pointB.format()
                + "]]";
    }
}
