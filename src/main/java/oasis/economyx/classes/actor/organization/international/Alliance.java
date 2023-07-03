package oasis.economyx.classes.actor.organization.international;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.organization.AbstractOrganization;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.warfare.Faction;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A group of nations formed to project greater collective power
 * Declaration of war against any member state will result in war with every member state
 */
public final class Alliance extends AbstractOrganization<Sovereign> implements Faction {
    /**
     * Creates a new alliance
     *
     * @param uniqueId      Unique ID of this alliance
     * @param name          Name of this alliance
     * @param currency      Currency used to pay the representative of this organization
     * @param foundingState Founding state (cannot be null)
     */
    public Alliance(UUID uniqueId, @Nullable String name, @NonNull Cash currency, @NonNull Sovereign foundingState) {
        super(uniqueId, name, currency);

        addMember(foundingState);
        setRepresentative(foundingState.getRepresentative());

        this.hostilities = new ArrayList<>();
    }

    public Alliance() {
        super();
        this.hostilities = new ArrayList<>();
    }

    public Alliance(Alliance other) {
        super(other);

        this.hostilities = other.hostilities;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private List<Faction> hostilities;

    @NonNull
    @Override
    @JsonIgnore
    public List<Faction> getHostilities() {
        return new ArrayList<>(hostilities);
    }

    @Override
    @JsonIgnore
    public void addHostility(@NonNull Faction faction) {
        this.hostilities.add(faction);
    }

    @Override
    @JsonIgnore
    public void removeHostility(@NonNull Faction faction) {
        this.hostilities.remove(faction);
    }

    @JsonProperty
    private final Type type = Type.ALLIANCE;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        super.initialize(state);

        List<Sovereign> refs = getMembers();
        getRawMembers().clear();

        for (Sovereign orig : state.getSovereigns()) {
            for (Sovereign ref : refs) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    getRawMembers().add(orig);
                }
            }
        }
    }
}
