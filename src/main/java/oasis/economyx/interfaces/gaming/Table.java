package oasis.economyx.interfaces.gaming;

/**
 * A game is hosted by a house (Usually a casino)
 */
public interface Table {
    /**
     * Gets the type of this game
     * @return Table type
     */
    TableType getType();
}
