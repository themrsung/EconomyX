package oasis.economyx.listeners.property;

import oasis.economyx.EconomyX;
import oasis.economyx.events.property.PropertyProtectorChangedEvent;
import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.types.asset.property.PropertyMeta;
import oasis.economyx.types.asset.property.PropertyStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PropertyProtectionHandler extends EconomyListener {
    public PropertyProtectionHandler(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPropertyProtectorChanged(PropertyProtectorChangedEvent e) {
        if (e.isCancelled()) return;

        PropertyProtector protector = e.getProtector();
        PropertyStack property = e.getProperty();

        PropertyMeta meta = property.getMeta();
        meta.setProtector(protector);
        property.setMeta(meta);
    }


}
