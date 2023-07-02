package oasis.economyx.listeners.banknote;

import oasis.economyx.EconomyX;
import oasis.economyx.events.banknote.BanknoteIssuedEvent;
import oasis.economyx.interfaces.actor.types.institutional.BanknoteIssuer;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BanknoteIssuedListener extends EconomyListener {
    public BanknoteIssuedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onBanknoteIssued(BanknoteIssuedEvent e) {
        if (e.isCancelled()) return;

        BanknoteIssuer issuer = e.getIssuer();
        Player physicalIssuer = e.getPhysicalIssuer();

        physicalIssuer.getInventory().addItem(issuer.issueBanknote(e.getBanknote()));
    }
}
