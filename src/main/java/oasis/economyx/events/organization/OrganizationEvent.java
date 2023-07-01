package oasis.economyx.events.organization;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.organization.Organization;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class OrganizationEvent<O extends Organization<?>> extends EconomyEvent {
    public OrganizationEvent(@NonNull O organization) {
        this.organization = organization;
    }
    @NonNull
    private final O organization;

    @NonNull
    public O getOrganization() {
        return organization;
    }
}
