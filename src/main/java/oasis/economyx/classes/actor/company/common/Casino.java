package oasis.economyx.classes.actor.company.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.ActorType;
import oasis.economyx.interfaces.actor.types.services.House;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.gaming.Table;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A casino can host games to be played by their customers
 */
public final class Casino extends Company implements House {
    /**
     * Creates a new casino
     * @param uniqueId Unique ID of this casino
     * @param name Name of this casino (not unique)
     * @param stockId ID of this casino's stock
     * @param shareCount Initial share count of this casino
     * @param currency The currency this casino should use
     */
    public Casino(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.tables = new ArrayList<>();
    }

    public Casino() {
        super();

        this.tables = new ArrayList<>();
    }

    public Casino(Casino other) {
        super(other);
        this.tables = other.tables;
    }

    /**
     * A list of all tables open in this casino
     */
    @JsonProperty
    private final List<Table> tables;

    @Override
    @JsonIgnore
    public List<Table> getTables() {
        return new ArrayList<>(tables);
    }

    @Override
    @JsonIgnore
    public void addTable(Table table) {
        tables.add(table);
    }

    @Override
    @JsonIgnore
    public void removeTable(Table table) {
        tables.remove(table);
    }

    @JsonProperty
    private final ActorType type = ActorType.CASINO;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
