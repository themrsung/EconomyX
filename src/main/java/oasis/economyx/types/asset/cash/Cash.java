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
        this.name = null;
    }

    public Cash(@NonNull UUID uniqueId, @NonNull String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public Cash(Cash other) {
        this.uniqueId = other.uniqueId;
        this.name = other.name;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @NonNull
    @JsonProperty
    private String name;

    @Override
    @JsonIgnore
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    @JsonIgnore
    public @NonNull String getName() {
        return name;
    }

    @Override
    @JsonIgnore
    public void setName(@NonNull String name) {
        this.name = name;
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
