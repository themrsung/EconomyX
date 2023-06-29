package oasis.economyx.classes.actor.sovereignty.singular;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.ActorType;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.sovereignty.Sovereignty;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Republic extends Sovereignty {
    /**
     * Creates a new republic
     * @param uniqueId Unique ID of this republic
     * @param name Name of this republic (not unique)
     * @param currency Currency used in this republic
     * @param founder Founding representative (cannot be null)
     */
    public Republic(UUID uniqueId, @Nullable String name, @NonNull Cash currency, @NonNull Person founder) {
        super(uniqueId, name, currency);

        this.citizens = new ArrayList<>();
        addCitizen(founder);

        this.corporations = new ArrayList<>();
        this.institutions = new ArrayList<>();
    }

    public Republic() {
        super();

        this.citizens = new ArrayList<>();
        this.corporations = new ArrayList<>();
        this.institutions = new ArrayList<>();
    }

    public Republic(Republic other) {
        super(other);
        this.citizens = other.citizens;
        this.corporations = other.corporations;
        this.institutions = other.institutions;
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

    @NotNull
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

    @NotNull
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

    @NotNull
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


    @JsonProperty
    private final ActorType type = ActorType.REPUBLIC;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
