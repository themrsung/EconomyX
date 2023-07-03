package oasis.economyx.types.asset;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.commodity.Commodity;
import oasis.economyx.types.asset.contract.collateral.Collateral;
import oasis.economyx.types.asset.contract.forward.Forward;
import oasis.economyx.types.asset.contract.note.Note;
import oasis.economyx.types.asset.contract.option.Option;
import oasis.economyx.types.asset.contract.swap.Swap;
import oasis.economyx.types.asset.property.Property;
import oasis.economyx.types.asset.stock.Stock;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * An asset can represent a right to ownership
 * Immutable
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Cash.class, name = "CASH"),
        @JsonSubTypes.Type(value = Commodity.class, name = "COMMODITY"),
        @JsonSubTypes.Type(value = Stock.class, name = "STOCK"),
        @JsonSubTypes.Type(value = Property.class, name = "PROPERTY"),
        @JsonSubTypes.Type(value = Collateral.class, name = "COLLATERAL"),
        @JsonSubTypes.Type(value = Forward.class, name = "FORWARD"),
        @JsonSubTypes.Type(value = Note.class, name = "NOTE"),
        @JsonSubTypes.Type(value = Option.class, name = "OPTION"),
        @JsonSubTypes.Type(value = Swap.class, name = "SWAP"),
})
public interface Asset {
    /**
     * The unique identifier of this asset type
     * Multiple instances of the same asset can be created
     * This is used as a symbol to discern asset subtype (e.g. USD vs EUR)
     *
     * @return The unique ID of this asset
     */
    @NonNull
    UUID getUniqueId();

    /**
     * The classification of this asset (e.g. Cash)
     *
     * @return Classification
     */
    Asset.Type getType();

    /**
     * Gets the display name of this asset.
     * @return Name
     */
    @NonNull
    String getName();

    /**
     * Sets the display name of this asset.
     * @param name Name
     */
    void setName(@NonNull String name);

    /**
     * Copies this asset
     *
     * @return An identical copy of this asset
     */
    @NonNull
    Asset copy();

    /**
     * Ensures that only UUID will be compared.
     * @param other Other asset
     * @return Whether this asset equals the other.
     */
    default boolean equals(@NonNull Asset other) {
        return getUniqueId().equals(other.getUniqueId());
    }

    enum Type {
        // Basic assets
        CASH,
        STOCK,
        COMMODITY,
        PROPERTY,

        // Contracts
        FORWARD,
        NOTE,
        OPTION,
        SWAP,
        COLLATERAL
    }
}
