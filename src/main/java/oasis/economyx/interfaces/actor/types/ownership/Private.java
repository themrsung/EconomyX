package oasis.economyx.interfaces.actor.types.ownership;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.reference.References;

/**
 * A private actor is owned by one actor
 */
public interface Private extends Representable, References {
    /**
     * Gets the owner of this actor
     *
     * @return Owner
     */
    @JsonIgnore
    Actor getOwner();

    /**
     * Sets the owner of this actor
     *
     * @param owner New owner
     */
    @JsonIgnore
    void setOwner(Actor owner);
}
