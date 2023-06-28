package oasis.economyx.actor.types;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonManagedReference;
import com.fasterxml.jackson.annotation.JsonProperty;
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
    @JsonProperty("employees")
    @JsonIdentityReference
    List<Person> getEmployees();

    /**
     * Adds an employee
     * @param employee Employee to hire
     */
    @JsonIgnore
    void addEmployee(Person employee);

    /**
     * Removes an employee
     * @param employee Employee to fire
     */
    @JsonIgnore
    void removeEmployee(Person employee);

    /**
     * Gets the directors of this employer
     * @return Copied list of directors
     */
    @JsonProperty("directors")
    @JsonIdentityReference
    List<Person> getDirectors();

    /**
     * Adds a director to the board
     * @param director Director to hire
     */
    @JsonIgnore
    void addDirector(Person director);

    /**
     * Removes a director from the board
     * @param director Director to fire
     */
    @JsonIgnore
    void removeDirector(Person director);

    /**
     * Gets every member os this employer
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
}
