package oasis.economyx.actor.types.institutional;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * A legislative actor can exercise legislative rights within its sovereignty
 */
public interface Legislative extends Institutional {
    /**
     * Gets all laws currently valid
     * @return Copied list of laws
     */
    @NonNull
    List<String> getLaws();

    /**
     * Adds a law
     * @param law Law to add
     */
    void passLaw(@NonNull String law);

    /**
     * Removes a law
     * @param law law to remove
     */
    void repealLaw(@NonNull String law);
}
