package oasis.economyx.state;

import oasis.economyx.actor.Actor;

import java.util.List;

public interface EconomyState {

    /**
     * Gets all actors currently present within this state
     * @return Copied list of actors
     */
    List<Actor> getActors();



}
