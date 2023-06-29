package oasis.economyx.interfaces.actor.types.sovereign;

import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
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
 *     Federal sovereigns are coded to behave as a singular sovereign.
 *     However, there are certain limitations to this implementation.
 *     Please check the comments before assuming that a method will behave as you intend it to.
 *     Feel free to change the behavior to suit your needs!
 * </p>
 */
public interface Federal extends Sovereign {
    /**
     * Gets all member states
     * @return A copied list of member states
     */
    @NonNull
    List<Sovereign> getMemberStates();

    /**
     * Adds a state to this federal
     * @param state State to add
     */
    void addMemberState(@NonNull Sovereign state);

    /**
     * Removes a state from this federal
     * @param state State to remove
     */
    void removeMemberState(@NonNull Sovereign state);

    /**
     * Gets the state representing this federal
     * @return State if found, null if not
     */
    @Nullable
    Sovereign getRepresentativeState();

    /**
     * Gets the personal representative of the representative state
     * @return Representative person if found, null if not
     */
    @Override
    @Nullable
    default Person getRepresentative() {
        if (getRepresentativeState() == null) return null;

        return getRepresentativeState().getRepresentative();
    }

    /**
     * Sets the representative state
     * @param state State to set
     * @throws IllegalArgumentException When the state is not a member of this federal
     */
    void setRepresentativeState(@NonNull Sovereign state) throws IllegalArgumentException;

    /**
     * Sets the representative of the representative state
     * @param representative Representative
     */
    @Override
    default void setRepresentative(@Nullable Person representative) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().setRepresentative(representative);
    }

    /**
     * Gets all citizens of this federal
     * @return Copied list of citizens
     */
    @Override
    @NonNull
    default List<Person> getCitizens() {
        List<Person> citizens = new ArrayList<>();

        for (Sovereign m : getMemberStates()) {
            citizens.addAll(m.getCitizens());
        }

        return citizens;
    }

    /**
     * Adds a citizen to the representative state
     * @param citizen Citizen to add
     */
    @Override
    default void addCitizen(@NonNull Person citizen) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().addCitizen(citizen);
    }

    /**
     * Removes a citizen from all member states
     * @param citizen Citizen to remove
     */
    @Override
    default void removeCitizen(@NonNull Person citizen) {
        for (Sovereign m : getMemberStates()) {
            m.removeCitizen(citizen);
        }
    }

    /**
     * Gets every corporation in this federal
     * @return Copied list of all corporations
     */
    @Override
    @NonNull
    default List<Corporation> getCorporations() {
        List<Corporation> corporations = new ArrayList<>();

        for (Sovereign m : getMemberStates()) {
            corporations.addAll(m.getCorporations());
        }

        return corporations;
    }

    /**
     * Adds a corporation to the representative state
     * @param corporation Corporation to add
     */
    @Override
    default void addCorporation(@NonNull Corporation corporation) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().addCorporation(corporation);
    }

    /**
     * Removes a corporation from all member states
     * @param corporation Corporation to remove
     */
    @Override
    default void removeCorporation(@NonNull Corporation corporation) {
        for (Sovereign m : getMemberStates()) {
            m.removeCorporation(corporation);
        }
    }

    /**
     * Gets all institutions in this federal
     * @return Copied list of all institutions
     */
    @Override
    @NonNull
    default List<Institutional> getInstitutions() {
        List<Institutional> institutions = new ArrayList<>();

        for (Sovereign m : getMemberStates()) {
            institutions.addAll(m.getInstitutions());
        }

        return institutions;
    }

    /**
     * Adds an institution to the representative state
     * @param institution Institution to add
     */
    @Override
    default void addInstitution(@NonNull Institutional institution) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().addInstitution(institution);
    }

    /**
     * Removes an institution from all member states
     * @param institution Institution to remove
     */
    @Override
    default void removeInstitution(@NonNull Institutional institution) {
        for (Sovereign m : getMemberStates()) {
            m.removeInstitution(institution);
        }
    }

    /**
     * Gets the representative pay of the representative state
     * @return Hourly pay
     */
    @Override
    @NonNull
    default CashStack getRepresentativePay() {
        if (getRepresentativeState() == null) throw new RuntimeException();
        return getRepresentativeState().getRepresentativePay();
    }

    /**
     * Sets the representative pay of the representative state
     * @param pay Hourly pay
     */
    @Override
    default void setRepresentativePay(@NonNull CashStack pay) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().setRepresentativePay(pay);
    }

    /**
     * Gets the protection fee of the representative state
     * @return Protection fee
     */
    @Override
    @NonNull
    default CashStack getProtectionFee() {
        if (getRepresentativeState() == null) throw new RuntimeException();
        return getRepresentativeState().getProtectionFee();
    }

    /**
     * Sets the protection fee of the representative state
     * @param fee Protection fee
     */
    @Override
    default void setProtectionFee(@NonNull CashStack fee) {
        if (getRepresentativeState() == null) throw new RuntimeException();
        getRepresentativeState().setProtectionFee(fee);
    }


}
