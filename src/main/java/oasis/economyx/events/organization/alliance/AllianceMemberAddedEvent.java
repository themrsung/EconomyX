package oasis.economyx.events.organization.alliance;

import oasis.economyx.classes.actor.organization.international.Alliance;
import oasis.economyx.events.organization.OrganizationEvent;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AllianceMemberAddedEvent extends OrganizationEvent<Alliance> {
    public AllianceMemberAddedEvent(@NonNull Alliance organization, @NonNull Sovereign member) {
        super(organization);
        this.member = member;
    }

    @NonNull
    private final Sovereign member;

    @NonNull
    public Sovereign getMember() {
        return member;
    }
}
