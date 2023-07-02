package oasis.economyx.listeners.organization;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.organization.international.Alliance;
import oasis.economyx.events.organization.alliance.AllianceMemberAddedEvent;
import oasis.economyx.events.organization.alliance.AllianceMemberRemovedEvent;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AllianceMemberChangedListener extends EconomyListener {
    public AllianceMemberChangedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAllianceMemberAdded(AllianceMemberAddedEvent e) {
        if (e.isCancelled()) return;

        Alliance alliance = e.getOrganization();
        Sovereign member = e.getMember();

        alliance.addMember(member);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAllianceMemberRemoved(AllianceMemberRemovedEvent e) {
        if (e.isCancelled()) return;

        Alliance alliance = e.getOrganization();
        Sovereign member = e.getMember();

        alliance.removeMember(member);
    }
}
