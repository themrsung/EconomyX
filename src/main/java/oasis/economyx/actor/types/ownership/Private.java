package oasis.economyx.actor.types.ownership;

import oasis.economyx.actor.Actor;
import oasis.economyx.actor.types.governance.Representable;

/**
 * A private actor is owned by one actor
 */
public interface Private extends Representable {
    /**
     * Gets the owner of this actor
     * @return Owner
     */
    Actor getOwner();

    /**
     * Sets the owner of this actor
     * @param owner New owner
     */
    void setOwner(Actor owner);
}
