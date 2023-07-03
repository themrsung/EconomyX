package oasis.economyx.classes.actor.sovereignty.singular;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.sovereignty.Sovereignty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.governance.Democratic;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Republic extends Sovereignty implements Democratic {
    /**
     * Creates a new republic
     *
     * @param uniqueId Unique ID of this republic
     * @param name     Name of this republic (not unique)
     * @param currency Currency used in this republic
     * @param founder  Founding representative (cannot be null)
     */
    public Republic(UUID uniqueId, @Nullable String name, @NonNull Cash currency, @NonNull Person founder) {
        super(uniqueId, name, currency);

        this.citizens = new ArrayList<>();
        addCitizen(founder);

        this.corporations = new ArrayList<>();
        this.institutions = new ArrayList<>();

        this.openVotes = new ArrayList<>();
    }

    public Republic() {
        super();

        this.citizens = new ArrayList<>();
        this.corporations = new ArrayList<>();
        this.institutions = new ArrayList<>();

        this.openVotes = new ArrayList<>();
    }

    public Republic(Republic other) {
        super(other);

        this.citizens = other.citizens;
        this.corporations = other.corporations;
        this.institutions = other.institutions;

        this.openVotes = other.openVotes;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final List<Person> citizens;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final List<Corporation> corporations;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final List<Institutional> institutions;

    @NonNull
    @JsonProperty
    private final List<Vote> openVotes;

    @NonNull
    @Override
    @JsonIgnore
    public List<Person> getCitizens() {
        return new ArrayList<>(citizens);
    }

    @Override
    @JsonIgnore
    public void addCitizen(@NonNull Person citizen) {
        citizens.add(citizen);
    }

    @Override
    @JsonIgnore
    public void removeCitizen(@NonNull Person citizen) {
        citizens.remove(citizen);
    }

    @NonNull
    @Override
    @JsonIgnore
    public List<Corporation> getCorporations() {
        return new ArrayList<>(corporations);
    }

    @Override
    @JsonIgnore
    public void addCorporation(@NonNull Corporation corporation) {
        corporations.add(corporation);
    }

    @Override
    @JsonIgnore
    public void removeCorporation(@NonNull Corporation corporation) {
        corporations.remove(corporation);
    }

    @NonNull
    @Override
    @JsonIgnore
    public List<Institutional> getInstitutions() {
        return new ArrayList<>(institutions);
    }

    @Override
    @JsonIgnore
    public void addInstitution(@NonNull Institutional institution) {
        institutions.add(institution);
    }

    @Override
    @JsonIgnore
    public void removeInstitution(@NonNull Institutional institution) {
        institutions.remove(institution);
    }

    @NonNull
    @Override
    @JsonIgnore
    public List<Vote> getOpenVotes() {
        return new ArrayList<>(openVotes);
    }

    @Override
    @JsonIgnore
    public void openVote(@NonNull Vote vote) {
        this.openVotes.add(vote);
    }

    @Override
    @JsonIgnore
    public void cleanVotes() {
        openVotes.removeIf(v -> v.getExpiry().isBeforeNow());
        openVotes.removeIf(v -> v.getCandidates().size() == 0);
    }

    @JsonProperty
    private final Type type = Type.REPUBLIC;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        super.initialize(state);

        List<Person> citizenRefs = getCitizens();
        List<Corporation> corporationRefs = getCorporations();
        List<Institutional> institutionRefs = getInstitutions();

        citizens.clear();
        corporations.clear();
        institutions.clear();

        for (Person orig : state.getPersons()) {
            for (Person ref : citizenRefs) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    citizens.add(orig);
                }
            }
        }

        for (Corporation orig : state.getCorporations()) {
            for (Corporation ref : corporationRefs) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    corporations.add(orig);
                }
            }
        }

        for (Institutional orig : state.getInstitutionals()) {
            for (Institutional ref : institutionRefs) {
                if (orig.getUniqueId().equals(ref.getUniqueId())) {
                    institutions.add(orig);
                }
            }
        }
    }
}
