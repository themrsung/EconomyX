package oasis.economyx.actor.types.ownership;

import oasis.economyx.actor.Actor;
import oasis.economyx.actor.types.governance.Representable;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

/**
 * Represents shared ownership of a representable
 */
public interface Shared extends Representable {
    /**
     * Gets the symbol of this actor's stock
     * @return Stock's unique ID
     */
    @NonNull
    UUID getStockId();

    /**
     * Gets the total issued share count
     * This can differ from the actual number of shares in circulation
     * The difference can come from shares being lost, counterfeiting shares, or non-backed short selling
     * @return Total issued share count
     */
    @NonNegative
    long getShareCount();

    /**
     * Sets the share count of this actor
     * @param shares New share count
     */
    void setShareCount(@NonNegative long shares);

    /**
     * Increases the share count of this actor
     * Please note that this does not trigger the actual increase of number of shares in circulation
     * @param delta Amount of shares to add
     */
    void addShareCount(@NonNegative long delta);

    /**
     * Gets a list of every shareholder
     * List is sorted by share count descending
     * @param state Current running state
     * @return List of shareholders
     */
    List<Actor> getShareholders(EconomyState state);

    /**
     * Gets a list of majority shareholders
     * While usually singular, there can be multiple shareholders with the same share count
     * Self owned shares are not counted
     *
     * @param state Current running state
     * @return List of majority shareholders
     */
    List<Actor> getMajorityShareholders(EconomyState state);
}
