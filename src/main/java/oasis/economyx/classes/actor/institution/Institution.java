package oasis.economyx.classes.actor.institution;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.person.Person;
import oasis.economyx.actor.sovereign.Sovereign;
import oasis.economyx.actor.types.institutional.Institutional;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.asset.cash.CashStack;
import oasis.economyx.classes.EconomicActor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An institution requires a parent sovereign
 */
public abstract class Institution extends EconomicActor implements Institutional {
    public Institution(@NonNull Sovereign parent, UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(uniqueId, name);

        this.parent = parent;
        this.representative = null;
        this.representativePay = new CashStack(currency, 0L);

        this.employees = new ArrayList<>();
        this.directors = new ArrayList<>();

        this.employeePay = new CashStack(currency, 0L);
        this.directorPay = new CashStack(currency, 0L);
    }

    public Institution() {
        this.parent = null;
        this.representative = null;
        this.representativePay = null;

        this.employees = new ArrayList<>();
        this.directors = new ArrayList<>();

        this.employeePay = null;
        this.directorPay = null;
    }

    public Institution(Institution other) {
        super(other);
        this.parent = other.parent;
        this.employees = other.employees;
        this.employeePay = other.employeePay;
        this.directors = other.directors;
        this.directorPay = other.directorPay;
        this.representative = other.representative;
        this.representativePay = other.representativePay;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Sovereign parent;

    @NonNull
    @JsonIgnore
    public Sovereign getParent() {
        return parent;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final List<Person> employees;
    @NonNull
    @JsonProperty
    private CashStack employeePay;

    @Override
    @JsonIgnore
    public List<Person> getEmployees() {
        return new ArrayList<>(employees);
    }

    @Override
    @JsonIgnore
    public @NonNull CashStack getEmployeePay() {
        return new CashStack(employeePay);
    }

    @Override
    @JsonIgnore
    public void setEmployeePay(@NonNull CashStack employeePay) {
        this.employeePay = employeePay;
    }

    @Override
    @JsonIgnore
    public void addEmployee(Person employee) {
        employees.add(employee);
    }

    @Override
    @JsonIgnore
    public void removeEmployee(Person employee) {
        employees.remove(employee);
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final List<Person> directors;
    @NonNull
    @JsonProperty
    private CashStack directorPay;

    @Override
    @JsonIgnore
    public List<Person> getDirectors() {
        return new ArrayList<>(directors);
    }

    @NotNull
    @Override
    @JsonIgnore
    public CashStack getDirectorPay() {
        return new CashStack(directorPay);
    }

    @Override
    @JsonIgnore
    public void setDirectorPay(@NonNull CashStack directorPay) {
        this.directorPay = directorPay;
    }

    @Override
    @JsonIgnore
    public void addDirector(Person director) {
        directors.add(director);
    }

    @Override
    @JsonIgnore
    public void removeDirector(Person director) {
        directors.remove(director);
    }

    @Nullable
    @JsonProperty
    @JsonIdentityReference
    private Person representative;
    @NonNull
    @JsonProperty
    private CashStack representativePay;

    @Nullable
    @Override
    @JsonIgnore
    public Person getRepresentative() {
        return representative;
    }

    @Override
    @JsonIgnore
    public void setRepresentative(@Nullable Person representative) {
        this.representative = representative;
    }

    @Override
    @JsonIgnore
    public @NonNull CashStack getRepresentativePay() {
        return new CashStack(representativePay);
    }

    @Override
    @JsonIgnore
    public void setRepresentativePay(@NonNull CashStack representativePay) {
        this.representativePay = representativePay;
    }
}
