package oasis.economyx.interfaces.actor.types.employment;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * An employer is capable of employing persons, and automatically paying salaries
 */
public interface Employer extends Representable {
    /**
     * Gets the employees of this employer
     *
     * @return Copied list of employees
     */
    @JsonIgnore
    List<Person> getEmployees();

    /**
     * Adds an employee
     *
     * @param employee Employee to hire
     */
    @JsonIgnore
    void addEmployee(Person employee);

    /**
     * Removes an employee
     *
     * @param employee Employee to fire
     */
    @JsonIgnore
    void removeEmployee(Person employee);

    /**
     * Gets the directors of this employer
     *
     * @return Copied list of directors
     */
    @JsonIgnore
    List<Person> getDirectors();

    /**
     * Adds a director to the board
     *
     * @param director Director to hire
     */
    @JsonIgnore
    void addDirector(Person director);

    /**
     * Removes a director from the board
     *
     * @param director Director to fire
     */
    @JsonIgnore
    void removeDirector(Person director);

    /**
     * Gets the hourly pay of employees
     *
     * @return Hourly pay
     */
    @NonNull
    @JsonIgnore
    CashStack getEmployeePay();

    /**
     * Sets the hourly pay of employees
     *
     * @param pay Hourly pay
     */
    @JsonIgnore
    void setEmployeePay(@NonNull CashStack pay);

    /**
     * Gets the hourly pay of directors
     *
     * @return Hourly pay
     */
    @NonNull
    @JsonIgnore
    CashStack getDirectorPay();

    /**
     * Sets the hourly pay of directors
     *
     * @param pay Hourly pay
     */
    @JsonIgnore
    void setDirectorPay(@NonNull CashStack pay);

    /**
     * Gets every member os this employer
     *
     * @return List of every member
     */
    @JsonIgnore
    default List<Person> getMembers() {
        List<Person> members = new ArrayList<>();

        members.addAll(getEmployees());
        members.addAll(getDirectors());

        if (getRepresentative() != null) {
            members.add(getRepresentative());
        }

        return members;
    }

    /**
     * Pays employees and directors.
     * Called every hour.
     */
    @JsonIgnore
    default void paySalaries() {
        if (getEmployeePay().getQuantity() > 0L) {
            for (Person e : getEmployees()) {
                Bukkit.getPluginManager().callEvent(new PaymentEvent(
                        this,
                        e,
                        getEmployeePay(),
                        PaymentEvent.Cause.SALARY_PAYMENT
                ));
            }
        }

        if (getDirectorPay().getQuantity() > 0L) {
            for (Person d : getDirectors()) {
                Bukkit.getPluginManager().callEvent(new PaymentEvent(
                        this,
                        d,
                        getDirectorPay(),
                        PaymentEvent.Cause.SALARY_PAYMENT
                ));
            }
        }
    }
}
