package oasis.economyx.asset.stock;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.asset.Asset;
import oasis.economyx.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Right to partial ownership of a corporation
 */
public final class Stock implements Asset {
    public Stock() {
        this.uniqueId = UUID.randomUUID();
    }

    public Stock(@NonNull UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Stock(Stock other) {
        this.uniqueId = other.uniqueId;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @Override
    @JsonIgnore
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }


    @JsonProperty
    private final AssetType type = AssetType.STOCK;

    @Override
    @NonNull
    @JsonIgnore
    public AssetType getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull Stock copy() {
        return new Stock(this);
    }
}
