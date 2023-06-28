package oasis.economyx.asset.cash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.asset.Asset;
import oasis.economyx.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Cash is a fungible, stackable asset
 */
public final class Cash implements Asset {
    public Cash() {
        this.uniqueId = UUID.randomUUID();
    }

    public Cash(@NonNull UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Cash(Cash other) {
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
    private final AssetType type = AssetType.CASH;

    @Override
    @JsonIgnore
    public @NonNull AssetType getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull Cash copy() {
        return new Cash(this);
    }
}
