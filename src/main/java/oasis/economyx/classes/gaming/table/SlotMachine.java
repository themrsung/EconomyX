package oasis.economyx.classes.gaming.table;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.gaming.House;
import oasis.economyx.interfaces.gaming.table.Table;
import oasis.economyx.types.asset.chip.ChipStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

public final class SlotMachine implements Table {
    /**
     * The maximum number of players a table will allow.
     * Slot machines are a solo game.
     * Code is not guaranteed to work with multiple players.
     */
    @JsonIgnore
    public static final int MAX_PLAYERS = 1;

    public SlotMachine() {
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

    @Override
    public @NonNull ChipStack getChips() {
        return null;
    }

    @Override
    public void progressGame() {

    }

    private final Type type = Type.SLOT_MACHINE;

    @Override
    public Type getType() {
        return type;
    }
}
