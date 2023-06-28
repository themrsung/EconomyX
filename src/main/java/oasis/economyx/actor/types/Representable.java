package oasis.economyx.actor.types;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.Actor;
import oasis.economyx.actor.person.Person;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A representable actor requires a person to execute economic actions
 */
public interface Representable extends Actor {
    /**
     * Gets the person with authority to execute actions on this actor's behalf
     * @return Current representative
     */
    @Nullable
    @JsonProperty("representative")
    @JsonIdentityReference
    Person getRepresentative();

    /**
     * Sets the representative of this actor
     * @param representative New representative
     */
    @JsonIgnore
    void setRepresentative(@Nullable Person representative);
}
