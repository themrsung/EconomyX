package oasis.economyx.events.banknote;

import oasis.economyx.interfaces.actor.types.institutional.BanknoteIssuer;
import oasis.economyx.interfaces.physical.Banknote;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class BanknoteIssuedEvent extends BanknoteEvent {
    public BanknoteIssuedEvent(@NonNull Banknote banknote, @NonNull BanknoteIssuer issuer, @NonNull Player physicalIssuer) {
        super(banknote);
        this.issuer = issuer;
        this.physicalIssuer = physicalIssuer;
    }

    @NonNull
    private final BanknoteIssuer issuer;

    @NonNull
    private final Player physicalIssuer;

    @NonNull
    public BanknoteIssuer getIssuer() {
        return issuer;
    }

    @NonNull
    public Player getPhysicalIssuer() {
        return physicalIssuer;
    }
}
