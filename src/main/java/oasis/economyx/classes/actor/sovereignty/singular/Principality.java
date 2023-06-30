package oasis.economyx.classes.actor.sovereignty.singular;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.sovereignty.Sovereignty;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Principality extends Sovereignty {
    /**
     * Creates a new principality
     * @param uniqueId Unique ID of this principality
     * @param name Name of this principality (not unique)
     * @param currency Currency used in this principality
     * @param monarch Founding monarch (cannot be null)
     */
    public Principality(UUID uniqueId, @Nullable String name, @NonNull Cash currency, @NonNull Person monarch) {
        super(uniqueId, name, currency);

        this.citizens = new ArrayList<>();
        addCitizen(monarch);

        this.corporations = new ArrayList<>();
        this.institutions = new ArrayList<>();
    }

    public Principality() {
        super();

        this.citizens = new ArrayList<>();
        this.corporations = new ArrayList<>();
        this.institutions = new ArrayList<>();
    }

    public Principality(Principality other) {
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

    @JsonProperty
    private final Type type = Type.PRINCIPALITY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
