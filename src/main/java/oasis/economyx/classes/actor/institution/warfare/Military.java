package oasis.economyx.classes.actor.institution.warfare;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.institution.Institution;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
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
 * A sovereign can have multiple militaries (e.g. Army, Navy, Air force, etc.)
 */
public final class Military extends Institution implements Faction {
    /**
     * Creates a new military
     *
     * @param parent   Parent sovereign (cannot be null; Create a PMC for an independent faction)
     * @param uniqueId Unique ID of this military
     * @param name     Name of this military (not unique)
     * @param currency Currency of this military
     */
    public Military(@NonNull Sovereign parent, UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(parent, uniqueId, name, currency);

        this.hostilities = new ArrayList<>();
    }

    public Military() {
        super();
        this.hostilities = new ArrayList<>();
    }

    public Military(Military other) {
        super(other);

        this.hostilities = other.hostilities;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final List<Faction> hostilities;

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
    private final Type type = Type.MILITARY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public List<Person> getCombatants() {
        List<Person> combatants = new ArrayList<>();

        combatants.addAll(getEmployees());
        combatants.addAll(getDirectors());
        if (getRepresentative() != null) combatants.add(getRepresentative());

        combatants.addAll(getParent().getCitizens());

        return combatants;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        super.initialize(state);

        List<Faction> hostilityRefs = getHostilities();
        hostilities.clear();

        for (Faction orig : state.getFactions()) {
            for (Faction ref : hostilityRefs) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    hostilities.add(orig);
                }
            }
        }
    }
}
