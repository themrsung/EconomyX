package oasis.economyx.classes.gaming.table;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.services.House;
import oasis.economyx.interfaces.gaming.table.Table;
import oasis.economyx.types.asset.chip.ChipStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A blackjack table
 */
public final class BlackjackTable implements Table {
    /**
     * The maximum number of players a table will allow.
     * TODO
     * 5 is recommended.
     */
    @JsonIgnore
    public static final int MAX_PLAYERS = 5;

    public BlackjackTable() {
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

    @JsonProperty
    private final Type type = Type.BLACKJACK;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }
}
