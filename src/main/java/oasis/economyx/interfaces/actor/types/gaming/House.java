package oasis.economyx.interfaces.actor.types.gaming;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.gaming.table.Table;
import oasis.economyx.types.asset.chip.Chip;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * A house can host games
 */
public interface House extends Actor {
    /**
     * Gets all games currently open
     *
     * @return Copied list of games
     */
    @NonNull
    List<Table> getTables();

    /**
     * Adds a new table to this house
     *
     * @param table Table to add
     */
    void addTable(Table table);

    /**
     * Removes a table from this house
     *
     * @param table Table to remove
     */
    void removeTable(Table table);

    /**
     * Gets the type of chip used in this house
     *
     * @return Chip
     */
    @NonNull
    Chip getIssuedChip();
}
