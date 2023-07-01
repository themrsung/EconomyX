package oasis.economyx.events.organization.party;

import oasis.economyx.classes.actor.organization.personal.Party;
import oasis.economyx.events.organization.OrganizationEvent;
import oasis.economyx.interfaces.actor.person.Person;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PartyMemberRemovedEvent extends OrganizationEvent<Party> {
    public PartyMemberRemovedEvent(@NonNull Party organization, @NonNull Person member) {
        super(organization);
        this.member = member;
    }

    @NonNull
    private final Person member;

    @NonNull
    public Person getMember() {
        return member;
    }
}
