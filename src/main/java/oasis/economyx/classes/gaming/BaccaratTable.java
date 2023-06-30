package oasis.economyx.classes.gaming;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.services.House;
import oasis.economyx.interfaces.gaming.Table;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A baccarat table
 */
public final class BaccaratTable implements Table {
    public BaccaratTable() {
        this.uniqueId = UUID.randomUUID();
        this.casino = null;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final House casino;

    @Override
    @NonNull
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    @NonNull
    public House getCasino() {
        return casino;
    }

    @JsonProperty
    private final Type type = Type.BACCARAT;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }
}
