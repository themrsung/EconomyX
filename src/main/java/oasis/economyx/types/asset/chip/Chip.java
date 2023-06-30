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
    }

    public Chip(@NonNull UUID uniqueId) {
        this.uniqueId = uniqueId;
    }

    public Chip(Chip other) {
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
