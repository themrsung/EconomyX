package oasis.economyx.classes.actor.institution.tripartite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.Judicial;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.institution.Institution;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * A sovereign can have multiple judiciaries (e.g. regional court, supreme court, etc.)
 */
public final class Judiciary extends Institution implements Judicial {
    /**
     * Creates a new judiciary
     * @param parent Parent sovereign (cannot be null)
     * @param uniqueId Unique ID of this judiciary
     * @param name Name of this judiciary (cannot be null)
     * @param currency Currency of this judiciary
     */
    public Judiciary(@NonNull Sovereign parent, UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(parent, uniqueId, name, currency);
    }

    public Judiciary() {
    }

    public Judiciary(Judiciary other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.JUDICIARY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
