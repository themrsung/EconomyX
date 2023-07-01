package oasis.economyx.events.organization.cartel;

import oasis.economyx.classes.actor.organization.corporate.Cartel;
import oasis.economyx.events.organization.OrganizationEvent;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CartelMemberAddedEvent extends OrganizationEvent<Cartel> {
    public CartelMemberAddedEvent(@NonNull Cartel organization, @NonNull Corporation member) {
        super(organization);
        this.member = member;
    }

    @NonNull
    private final Corporation member;

    @NonNull
    public Corporation getMember() {
        return member;
    }
}
