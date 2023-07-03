package oasis.economyx.interfaces.actor.types.sovereign;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;

/**
 * A federal sovereign has multiple member states, and one representative member state.
 * It can either be democratic (Federation) or autocratic (Empire)
 *
 * <p>
 * Federal sovereigns are coded to behave as a singular sovereign.
 * However, there are certain limitations to this implementation.
 * Please check the comments before assuming that a method will behave as you intend it to.
 * Feel free to change the behavior to suit your needs!
 * </p>
 */
public interface Federal extends Sovereign, References {
    /**
     * Gets all member states
     *
     * @return A copied list of member states
     */
    @NonNull
    @JsonIgnore
    List<Sovereign> getMemberStates();

    /**
     * Adds a state to this federal
     *
     * @param state State to add
     */
    @JsonIgnore
    void addMemberState(@NonNull Sovereign state);

    /**
     * Removes a state from this federal
     *
     * @param state State to remove
     */
    @JsonIgnore
    void removeMemberState(@NonNull Sovereign state);

    /**
     * Gets the state representing this federal
     *
     * @return State if found, null if not
     */
    @Nullable
    @JsonIgnore
    Sovereign getRepresentativeState();

    /**
     * Gets the personal representative of the representative state
     *
     * @return Representative person if found, null if not
     */
    @Override
    @Nullable
    @JsonIgnore
    default Person getRepresentative() {
        if (getRepresentativeState() == null) return null;

        return getRepresentativeState().getRepresentative();
    }

    /**
     * Sets the representative state
     *
     * @param state State to set
     * @throws IllegalArgumentException When the state is not a member of this federal
     */
    @JsonIgnore
    void setRepresentativeState(@NonNull Sovereign state) throws IllegalArgumentException;

    /**
     * Sets the representative of the representative state
     *
     * @param representative Representative
     */
    @Override
    @JsonIgnore
    default void setRepresentative(@Nullable Person representative) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().setRepresentative(representative);
    }

    /**
     * Gets all citizens of this federal
     *
     * @return Copied list of citizens
     */
    @Override
    @NonNull
    @JsonIgnore
    default List<Person> getCitizens() {
        List<Person> citizens = new ArrayList<>();

        for (Sovereign m : getMemberStates()) {
            citizens.addAll(m.getCitizens());
        }

        return citizens;
    }

    /**
     * Adds a citizen to the representative state
     *
     * @param citizen Citizen to add
     */
    @Override
    @JsonIgnore
    default void addCitizen(@NonNull Person citizen) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().addCitizen(citizen);
    }

    /**
     * Removes a citizen from all member states
     *
     * @param citizen Citizen to remove
     */
    @Override
    @JsonIgnore
    default void removeCitizen(@NonNull Person citizen) {
        for (Sovereign m : getMemberStates()) {
            m.removeCitizen(citizen);
        }
    }

    /**
     * Gets every corporation in this federal
     *
     * @return Copied list of all corporations
     */
    @Override
    @NonNull
    @JsonIgnore
    default List<Corporation> getCorporations() {
        List<Corporation> corporations = new ArrayList<>();

        for (Sovereign m : getMemberStates()) {
            corporations.addAll(m.getCorporations());
        }

        return corporations;
    }

    /**
     * Adds a corporation to the representative state
     *
     * @param corporation Corporation to add
     */
    @Override
    @JsonIgnore
    default void addCorporation(@NonNull Corporation corporation) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().addCorporation(corporation);
    }

    /**
     * Removes a corporation from all member states
     *
     * @param corporation Corporation to remove
     */
    @Override
    @JsonIgnore
    default void removeCorporation(@NonNull Corporation corporation) {
        for (Sovereign m : getMemberStates()) {
            m.removeCorporation(corporation);
        }
    }

    /**
     * Gets all institutions in this federal
     *
     * @return Copied list of all institutions
     */
    @Override
    @NonNull
    @JsonIgnore
    default List<Institutional> getInstitutions() {
        List<Institutional> institutions = new ArrayList<>();

        for (Sovereign m : getMemberStates()) {
            institutions.addAll(m.getInstitutions());
        }

        return institutions;
    }

    /**
     * Adds an institution to the representative state
     *
     * @param institution Institution to add
     */
    @Override
    @JsonIgnore
    default void addInstitution(@NonNull Institutional institution) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().addInstitution(institution);
    }

    /**
     * Removes an institution from all member states
     *
     * @param institution Institution to remove
     */
    @Override
    @JsonIgnore
    default void removeInstitution(@NonNull Institutional institution) {
        for (Sovereign m : getMemberStates()) {
            m.removeInstitution(institution);
        }
    }

    /**
     * Gets the representative pay of the representative state
     *
     * @return Hourly pay
     */
    @Override
    @NonNull
    @JsonIgnore
    default CashStack getRepresentativePay() {
        if (getRepresentativeState() == null) throw new RuntimeException();
        return getRepresentativeState().getRepresentativePay();
    }

    /**
     * Sets the representative pay of the representative state
     *
     * @param pay Hourly pay
     */
    @Override
    @JsonIgnore
    default void setRepresentativePay(@NonNull CashStack pay) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().setRepresentativePay(pay);
    }

    /**
     * Gets the protection fee of the representative state
     *
     * @return Protection fee
     */
    @Override
    @NonNull
    @JsonIgnore
    default CashStack getProtectionFee() {
        if (getRepresentativeState() == null) throw new RuntimeException();
        return getRepresentativeState().getProtectionFee();
    }

    /**
     * Sets the protection fee of the representative state
     *
     * @param fee Protection fee
     */
    @Override
    @JsonIgnore
    default void setProtectionFee(@NonNull CashStack fee) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().setProtectionFee(fee);
    }


}
