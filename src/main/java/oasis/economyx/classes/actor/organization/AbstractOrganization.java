package oasis.economyx.classes.actor.organization;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.organization.Organization;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.classes.EconomicActor;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * An instantiable class of Organization
 */
public abstract class AbstractOrganization<M extends Actor> extends EconomicActor implements Organization<M> {
    public AbstractOrganization(UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(uniqueId, name);

        this.members = new ArrayList<>();
        this.representative = null;
        this.representativePay = new CashStack(currency, 0L);
    }

    public AbstractOrganization() {
        this.members = new ArrayList<>();
        this.representative = null;
        this.representativePay = null;
    }

    public AbstractOrganization(AbstractOrganization<M> other) {
        super(other);
        this.members = other.members;
        this.representative = other.representative;
        this.representativePay = other.representativePay;
    }

    @NonNull
    @JsonProperty
    private final List<M> members;

    @Nullable
    @JsonProperty
    private Person representative;

    @NonNull
    @JsonProperty
    private CashStack representativePay;

    @Override
    @JsonIgnore
    public @NonNull List<M> getMembers() {
        return new ArrayList<>(members);
    }

    @Override
    @JsonIgnore
    public void addMember(@NonNull M member) {
        members.add(member);
    }

    @Override
    @JsonIgnore
    public void removeMember(@NonNull M member) {
        members.remove(member);
    }

    @Override
    @JsonIgnore
    public @Nullable Person getRepresentative() {
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
    public void setRepresentativePay(@NonNull CashStack pay) {
        this.representativePay = pay;
    }
}
