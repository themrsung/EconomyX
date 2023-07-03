package oasis.economyx.classes.actor.organization.corporate;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.organization.AbstractOrganization;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * An organization of corporations formed with the goal of economic dominance
 * Usually used fulfill the collective needs of an industry
 */
public final class Cartel extends AbstractOrganization<Corporation> {
    /**
     * Creates a new cartel
     *
     * @param uniqueId Unique ID of this cartel
     * @param name     Name of thie cartel (not unique)
     * @param currency Currency used to pay the representative
     * @param founder  Founding corporation (cannot by null)
     */
    public Cartel(UUID uniqueId, @Nullable String name, @NonNull Cash currency, @NonNull Corporation founder) {
        super(uniqueId, name, currency);

        addMember(founder);
        setRepresentative(founder.getRepresentative());
    }

    public Cartel() {
        super();
    }

    public Cartel(Cartel other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.CARTEL;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        super.initialize(state);

        List<Corporation> refs = getMembers();
        getRawMembers().clear();

        for (Corporation orig : state.getCorporations()) {
            for (Corporation ref : refs) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    getRawMembers().add(orig);
                }
            }
        }
    }
}
