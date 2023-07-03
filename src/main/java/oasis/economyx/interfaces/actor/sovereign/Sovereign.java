package oasis.economyx.interfaces.actor.sovereign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A sovereign actor must be represented by a person, but is not owned by any other actor.
 */
public interface Sovereign extends Representable, PropertyProtector {
    /**
     * Gets all citizens of this sovereign
     *
     * @return A copied list of citizens
     */
    @NonNull
    @JsonIgnore
    List<Person> getCitizens();

    /**
     * Adds a citizen to this sovereign
     *
     * @param citizen Citizen to add
     */
    @JsonIgnore
    void addCitizen(@NonNull Person citizen);

    /**
     * Removes a citizen from this sovereign
     *
     * @param citizen Citizen to remove
     */
    @JsonIgnore
    void removeCitizen(@NonNull Person citizen);

    /**
     * Gets all corporations in this sovereign
     *
     * @return A copied list of corporations
     */
    @NonNull
    @JsonIgnore
    List<Corporation> getCorporations();

    /**
     * Adds a corporation to this sovereign
     *
     * @param corporation Corporation to add
     */
    @JsonIgnore
    void addCorporation(@NonNull Corporation corporation);

    /**
     * Removes a corporation from this sovereign
     *
     * @param corporation Corporation to remove
     */
    @JsonIgnore
    void removeCorporation(@NonNull Corporation corporation);

    /**
     * Gets all institutions of this sovereign
     *
     * @return A copied list of institutions
     */
    @NonNull
    @JsonIgnore
    List<Institutional> getInstitutions();

    /**
     * Adds an institution to this sovereign
     *
     * @param institution Institution to add
     */
    @JsonIgnore
    void addInstitution(@NonNull Institutional institution);

    /**
     * Removes an institution from this sovereign
     *
     * @param institution Institution to remove
     */
    @JsonIgnore
    void removeInstitution(@NonNull Institutional institution);

    /**
     * Gets all members of this sovereign
     *
     * @return A list of all members
     */
    @NonNull
    @JsonIgnore
    default List<Actor> getMembers() {
        List<Actor> members = new ArrayList<>();

        members.addAll(getCitizens());
        members.addAll(getCorporations());
        members.addAll(getInstitutions());

        return members;
    }


}
