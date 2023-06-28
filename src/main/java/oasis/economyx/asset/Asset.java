package oasis.economyx.asset;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.asset.commodity.Commodity;
import oasis.economyx.asset.contract.Contract;
import oasis.economyx.asset.stock.Stock;
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

@JsonSubTypes({
        @JsonSubTypes.Type(value = Cash.class, name = "cash"),
        @JsonSubTypes.Type(value = Commodity.class, name = "commodity"),
        @JsonSubTypes.Type(value = Stock.class, name = "stock"),
        @JsonSubTypes.Type(value = Contract.class, name = "contract")
})

public interface Asset {
    /**
     * The unique identifier of this asset type
     * Multiple instances of the same asset can be created
     * This is used as a symbol to discern asset subtype (e.g. USD vs EUR)
     * @return The unique ID of this asset
     */
    @NonNull
    @JsonProperty("uniqueId")
    UUID getUniqueId();

    /**
     * The classification of this asset (e.g. Cash)
     * @return Classification
     */
    @NonNull
    @JsonProperty("type")
    AssetType getType();
}
