package oasis.economyx.events.organization;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.organization.Organization;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class OrganizationEvent<M extends Actor> extends EconomyEvent {
    public OrganizationEvent(@NonNull Organization<M> organization) {
        this.organization = organization;
    }

    // TODO Member added, member removed for alliance, cartel, and party (total: 6 events)

    @NonNull
    private final Organization<M> organization;

    @NonNull
    public Organization<M> getOrganization() {
        return organization;
    }
}
