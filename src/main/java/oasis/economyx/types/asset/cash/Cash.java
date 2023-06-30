package oasis.economyx.types.asset.cash;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
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
    private final Type type = Type.CASH;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull Cash copy() {
        return new Cash(this);
    }
}
