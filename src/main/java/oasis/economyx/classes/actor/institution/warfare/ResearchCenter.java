package oasis.economyx.classes.actor.institution.warfare;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.ActorType;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.manufacturing.Scientific;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.institution.Institution;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * A sovereign can have multiple research centers
 */
public final class ResearchCenter extends Institution implements Scientific {
    /**
     * Creates a new research center
     * @param parent Parent sovereign (cannot be null)
     * @param uniqueId Unique ID of this research center
     * @param name Name of this research center
     * @param currency Currency of this research center
     */
    public ResearchCenter(@NonNull Sovereign parent, UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(parent, uniqueId, name, currency);
    }

    public ResearchCenter() {
        super();
    }

    public ResearchCenter(ResearchCenter other) {
        super(other);
    }

    @JsonProperty
    private final ActorType type = ActorType.RESEARCH_CENTER;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
