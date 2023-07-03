package oasis.economyx.classes.actor.organization.personal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.organization.AbstractOrganization;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.List;
import java.util.UUID;

/**
 * An organization of people created to fulfill a common goal
 */
public final class Party extends AbstractOrganization<Person> {
    /**
     * Creates a new party
     *
     * @param uniqueId Unique ID of this party
     * @param name     Name of this party
     * @param currency Currency used to pay the representative of this party
     * @param founder  Founder (cannot ba null)
     */
    public Party(UUID uniqueId, @Nullable String name, @NonNull Cash currency, @NonNull Person founder) {
        super(uniqueId, name, currency);

        addMember(founder);
        setRepresentative(founder);
    }

    public Party() {
        super();
    }

    public Party(Party other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.PARTY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        super.initialize(state);

        List<Person> refs = getMembers();
        getRawMembers().clear();

        for (Person orig : state.getPersons()) {
            for (Person ref : refs) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    getRawMembers().add(orig);
                }
            }
        }
    }
}
