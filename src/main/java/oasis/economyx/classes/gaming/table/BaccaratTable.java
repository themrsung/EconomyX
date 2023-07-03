package oasis.economyx.classes.gaming.table;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.gaming.House;
import oasis.economyx.interfaces.gaming.table.Table;
import oasis.economyx.types.asset.chip.ChipStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A baccarat table
 */
public final class BaccaratTable implements Table {
    /**
     * The maximum number of players a table will allow.
     * TODO
     * 9 is recommended.
     */
    @JsonIgnore
    public static final int MAX_PLAYERS = 9;

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
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    @NonNull
    @JsonIgnore
    public House getCasino() {
        return casino;
    }

    @Override
    @JsonIgnore
    public @NonNull ChipStack getChips() {
        return null;
    }

    @Override
    @JsonIgnore
    public void progressGame() {

    }

    @JsonProperty
    private final Type type = Type.BACCARAT;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }
}
