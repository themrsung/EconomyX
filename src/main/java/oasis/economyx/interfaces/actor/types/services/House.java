package oasis.economyx.interfaces.actor.types.services;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.gaming.table.Table;

import java.util.List;

/**
 * A house can host games
 */
public interface House extends Actor {
    /**
     * Gets all games currently open
     * @return Copied list of games
     */
    List<Table> getTables();

    /**
     * Adds a new table to this house
      * @param table Table to add
     */
    void addTable(Table table);

    /**
     * Removes a table from this house
      * @param table Table to remove
     */
    void removeTable(Table table);
}
