package oasis.economyx.types.asset.chip;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.types.asset.Asset;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Chips used in casinos
 * Fungible and stackable
 */
public final class Chip implements Asset {
    public Chip() {
        this.uniqueId = UUID.randomUUID();
        this.name = null;
    }

    public Chip(@NonNull UUID uniqueId, @NonNull String name) {
        this.uniqueId = uniqueId;
        this.name = name;
    }

    public Chip(Chip other) {
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
    public void setName(@NonNull String name) {
        this.name = name;
    }

    @JsonProperty
    private final Type type = Type.CHIP;

    @Override
    @JsonIgnore
    public Asset.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public @NonNull Chip copy() {
        return new Chip(this);
    }
}
