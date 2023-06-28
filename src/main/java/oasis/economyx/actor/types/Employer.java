package oasis.economyx.actor.types;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import oasis.economyx.actor.person.Person;

import java.util.ArrayList;
import java.util.List;

/**
 * An employer is capable of employing persons, and automatically paying salaries
 */
public interface Employer extends Representable {
    /**
     * Gets the employees of this employer
     * @return Copied list of employees
     */
    @JsonIdentityReference
    List<Person> getEmployees();

    /**
     * Adds an employee
     * @param employee Employee to hire
     */
    void addEmployee(Person employee);

    /**
     * Removes an employee
     * @param employee Employee to fire
     */
    void removeEmployee(Person employee);

    /**
     * Gets the directors of this employer
     * @return Copied list of directors
     */
    @JsonIdentityReference
    List<Person> getDirectors();

    /**
     * Adds a director to the board
     * @param director Director to hire
     */
    void addDirector(Person director);

    /**
     * Removes a director from the board
     * @param director Director to fire
     */
    void removeDirector(Person director);

    /**
     * Gets every member os this employer
     * @return List of every member
     */
    default List<Person> getMembers() {
        List<Person> members = new ArrayList<>();

        members.addAll(getEmployees());
        members.addAll(getDirectors());

        if (getRepresentative() != null) {
            members.add(getRepresentative());
        }

        return members;
    }
}
