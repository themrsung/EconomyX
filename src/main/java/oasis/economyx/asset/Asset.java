package oasis.economyx.asset;

import com.fasterxml.jackson.annotation.*;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.asset.commodity.Commodity;
import oasis.economyx.asset.contract.collateral.Collateral;
import oasis.economyx.asset.contract.forward.Forward;
import oasis.economyx.asset.contract.note.Note;
import oasis.economyx.asset.contract.option.Option;
import oasis.economyx.asset.contract.swap.Swap;
import oasis.economyx.asset.stock.Stock;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.io.Serializable;
import java.util.UUID;

/**
 * An asset can represent a right to ownership
 * Immutable
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = Cash.class, name = "CASH"),
        @JsonSubTypes.Type(value = Commodity.class, name = "COMMODITY"),
        @JsonSubTypes.Type(value = Stock.class, name = "STOCK"),
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
     * @return The unique ID of this asset
     */
    @NonNull
    UUID getUniqueId();

    /**
     * The classification of this asset (e.g. Cash)
     * @return Classification
     */
    @NonNull
    AssetType getType();

    /**
     * Copies this asset
     * @return An identical copy of this asset
     */
    @NonNull
    Asset copy();
}
