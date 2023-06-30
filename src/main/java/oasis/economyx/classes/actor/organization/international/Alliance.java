package oasis.economyx.classes.actor.organization.international;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.services.Faction;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.organization.AbstractOrganization;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * A group of nations formed to project greater collective power
 * Declaration of war against any member state will result in war with every member state
 */
public final class Alliance extends AbstractOrganization<Sovereign> implements Faction {
    /**
     * Creates a new alliance
     * @param uniqueId Unique ID of this alliance
     * @param name Name of this alliance
     * @param currency Currency used to pay the representative of this organization
     * @param foundingState Founding state (cannot be null)
     */
    public Alliance(UUID uniqueId, @Nullable String name, @NonNull Cash currency, @NonNull Sovereign foundingState) {
        super(uniqueId, name, currency);

        addMember(foundingState);
        setRepresentative(foundingState.getRepresentative());
    }

    public Alliance() {
        super();
    }

    public Alliance(Alliance other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.ALLIANCE;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
