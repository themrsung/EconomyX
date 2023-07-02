package oasis.economyx.listeners.property;

import oasis.economyx.EconomyX;
import oasis.economyx.events.property.PropertyAbandonedEvent;
import oasis.economyx.events.property.PropertyClaimedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.property.PropertyStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PropertyClaimHandler extends EconomyListener {
    public PropertyClaimHandler(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPropertyClaimed(PropertyClaimedEvent e) {
        if (e.isCancelled()) return;

        Actor claimer = e.getClaimer();
        PropertyStack property = e.getProperty();

        claimer.getAssets().add(property);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPropertyAbandoned(PropertyAbandonedEvent e) {
        if (e.isCancelled()) return;

        Actor abandoner = e.getHolder();
        PropertyStack property = e.getProperty();

        abandoner.getAssets().remove(property);
    }
}
