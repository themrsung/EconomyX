package oasis.economyx.classes.gaming;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.services.House;
import oasis.economyx.interfaces.gaming.Table;
import oasis.economyx.interfaces.gaming.TableType;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A blackjack table
 */
public final class BlackjackTable implements Table {
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

    @JsonProperty
    private final TableType type = TableType.BLACKJACK;

    @Override
    @JsonIgnore
    public TableType getType() {
        return type;
    }
}