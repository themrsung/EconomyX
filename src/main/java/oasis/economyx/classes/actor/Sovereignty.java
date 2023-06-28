package oasis.economyx.classes.actor;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.actor.person.Person;
import oasis.economyx.actor.sovereign.Sovereign;
import oasis.economyx.classes.EconomicActor;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * The instantiable class of Sovereign
 */
public final class Sovereignty extends EconomicActor implements Sovereign {
    /**
     * Constructs a new sovereignty
     * @param uniqueId Unique ID of this sovereignty
     * @param name Name of this sovereignty (not unique)
     */
    public Sovereignty(UUID uniqueId, @Nullable String name) {
        super(uniqueId, name);
    }

    public Sovereignty() {
        this.headOfState = null;
    }

    public Sovereignty(Sovereignty other) {
        super(other);
        this.headOfState = other.headOfState;
    }

    @Nullable
    @JsonIdentityReference
    private Person headOfState;

    @Override
    public @Nullable Person getRepresentative() {
        return headOfState;
    }

    @Override
    @JsonIgnore
    public void setRepresentative(@Nullable Person representative) {
        this.headOfState = representative;
    }

    @JsonProperty
    private final ActorType type = ActorType.SOVEREIGNTY;
    @Override
    @JsonIgnore
    public ActorType getType() {
        return type;
    }
}
