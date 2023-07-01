package oasis.economyx.types.address;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;

import java.util.Objects;

/**
 * Represents an area of land.
 * @param pointA Point A
 * @param pointB Point B
 */
public record Area(
        @JsonProperty Address pointA,
        @JsonProperty Address pointB
) {
    /**
     * Gets the area of this land.
     * @return Area
     * @throws RuntimeException When either of the points are invalid, or the world is different
     */
    @JsonIgnore
    double area() throws RuntimeException {
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
    Address center() throws RuntimeException {
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
}
