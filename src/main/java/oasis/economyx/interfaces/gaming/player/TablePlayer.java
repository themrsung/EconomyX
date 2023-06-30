package oasis.economyx.interfaces.gaming.player;

import oasis.economyx.types.asset.chip.ChipStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Represents a player at a casino table
 */
public interface TablePlayer {
    /**
     * Gets the unique ID of the player.
     * This is equal to the in-game player's unique ID.
     *
     * @return Unique ID
     */
    @NonNull
    UUID getPlayerId();

    /**
     * Gets the stack of chips this player has entered the game with.
     * @return Stack of chips
     */
    @NonNull
    ChipStack getStack();
}
