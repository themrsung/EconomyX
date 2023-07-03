package oasis.economyx.types.asset.property;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.google.errorprone.annotations.DoNotCall;
import oasis.economyx.types.address.Area;
import oasis.economyx.types.asset.Asset;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Right to ownership of a property
 */
public final class Property implements Asset {
    public Property() {
        this.uniqueId = UUID.randomUUID();
        this.area = null;
    }

    public Property(@NonNull UUID uniqueId, @NonNull Area area) {
        this.uniqueId = uniqueId;
        this.area = area;
    }

    public Property(Property other) {
        this.uniqueId = other.uniqueId;
        this.area = other.area;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @NonNull
    @JsonProperty
    private final Area area;

    @Override
    @JsonIgnore
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    @JsonIgnore
    public @NonNull String getName() {
        return getType().toString();
    }

    @Override
    @DoNotCall
    @JsonIgnore
    public void setName(@NonNull String name) {

    }

    @NonNull
    @JsonIgnore
    public Area getArea() {
        return area;
    }

    @JsonProperty
    private final Type type = Type.PROPERTY;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull Property copy() {
        return new Property(this);
    }
}
