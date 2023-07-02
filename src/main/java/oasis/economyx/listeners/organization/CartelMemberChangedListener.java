package oasis.economyx.listeners.organization;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.organization.corporate.Cartel;
import oasis.economyx.events.organization.cartel.CartelMemberAddedEvent;
import oasis.economyx.events.organization.cartel.CartelMemberRemovedEvent;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class CartelMemberChangedListener extends EconomyListener {
    public CartelMemberChangedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCartelMemberAdded(CartelMemberAddedEvent e) {
        if (e.isCancelled()) return;

        Cartel cartel = e.getOrganization();
        Corporation member = e.getMember();

        cartel.addMember(member);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onCartelMemberRemoved(CartelMemberRemovedEvent e) {
        if (e.isCancelled()) return;

        Cartel cartel = e.getOrganization();
        Corporation member = e.getMember();

        cartel.removeMember(member);
    }
}
