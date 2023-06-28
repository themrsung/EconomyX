package oasis.economyx.state;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.EconomyX;
import oasis.economyx.actor.Actor;
import org.apache.logging.log4j.Logger;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

public interface EconomyState {
    /**
     * Gets the plugin instance
     * @return Plugin
     */
    EconomyX getEX();

    /**
     * Shortcut getter for logger
     * @return Logger
     */
    default Logger getLogger() {
        return getEX().getLogger();
    }

    /**
     * Gets all actors currently present within this state
     * @return Copied list of actors
     */
    @JsonProperty("actors")
    List<Actor> getActors();

    /**
     * Checks if actor exists
     * @param uniqueId Unique ID of actor
     * @return Whether actor exists
     */
    @JsonIgnore
    boolean isActor(UUID uniqueId);

    /**
     * Gets actor by unique ID
     * Check if actor exists with isActor(UUID) first
     *
     * @param uniqueId Unique ID of actor
     * @return Actor
     * @throws IllegalArgumentException When actor cannot be found
     */
    @NonNull
    @JsonIgnore
    Actor getActor(UUID uniqueId) throws IllegalArgumentException;

    @JsonIgnore
    void addActor(@NonNull Actor actor);

    @JsonIgnore
    void removeActor(@NonNull Actor actor);

    /**
     * Attempt to save
     */
    void save();
}
