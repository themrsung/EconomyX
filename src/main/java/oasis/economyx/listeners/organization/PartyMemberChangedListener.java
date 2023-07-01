package oasis.economyx.listeners.organization;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.organization.corporate.Cartel;
import oasis.economyx.classes.actor.organization.personal.Party;
import oasis.economyx.events.organization.cartel.CartelMemberAddedEvent;
import oasis.economyx.events.organization.cartel.CartelMemberRemovedEvent;
import oasis.economyx.events.organization.party.PartyMemberAddedEvent;
import oasis.economyx.events.organization.party.PartyMemberRemovedEvent;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.listeners.EconomyListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PartyMemberChangedListener extends EconomyListener {
    public PartyMemberChangedListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPartyMemberAdded(PartyMemberAddedEvent e) {
        if (e.isCancelled()) return;

        Party party = e.getOrganization();
        Person member = e.getMember();

        party.addMember(member);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPartyMemberRemoved(PartyMemberRemovedEvent e) {
        if (e.isCancelled()) return;

        Party party = e.getOrganization();
        Person member = e.getMember();

        party.removeMember(member);
    }
}
