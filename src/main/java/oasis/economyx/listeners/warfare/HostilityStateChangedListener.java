package oasis.economyx.listeners.warfare;

import oasis.economyx.EconomyX;
import oasis.economyx.events.warfare.HostilityDeclaredEvent;
import oasis.economyx.events.warfare.HostilityRevokedEvent;
import oasis.economyx.interfaces.actor.types.warfare.Faction;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class HostilityStateChangedListener extends EconomyListener {
    public HostilityStateChangedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHostilityDeclared(HostilityDeclaredEvent e) {
        if (e.isCancelled()) return;

        Faction declarer = e.getInitiator();
        Faction hostile = e.getHostility();

        declarer.addHostility(hostile);
        hostile.addHostility(declarer);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onHostilityRevoked(HostilityRevokedEvent e) {
        if (e.isCancelled()) return;

        Faction revoked = e.getInitiator();
        Faction hostile = e.getHostility();

        revoked.removeHostility(hostile);
    }
}
