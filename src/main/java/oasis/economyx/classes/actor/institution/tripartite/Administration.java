package oasis.economyx.classes.actor.institution.tripartite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.ActorType;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.Administrative;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.institution.Institution;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * A sovereign can only have one administration
 */
public final class Administration extends Institution implements Administrative {
    /**
     * Creates a new administration
     * @param parent Parent sovereign (cannot be null)
     * @param uniqueId Unique ID of this administration
     * @param name Name of this administration (not unique)
     * @param currency Currency of this administration
     */
    public Administration(@NonNull Sovereign parent, UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(parent, uniqueId, name, currency);
    }

    public Administration() {
        super();
    }

    public Administration(Administration other) {
        super(other);
    }

    @JsonProperty
    private final ActorType type = ActorType.ADMINISTRATION;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
