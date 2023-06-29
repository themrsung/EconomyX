package oasis.economyx.types.asset.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import oasis.economyx.types.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Right to ownership of a property
 */
public final class Property implements Asset {
    public Property() {
        this.uniqueId = UUID.randomUUID();
    }

    public Property(@NonNull UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Property(Property other) {
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
    private final AssetType type = AssetType.PROPERTY;

    @Override
    @NonNull
    @JsonIgnore
    public AssetType getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull Property copy() {
        return new Property(this);
    }
}
