package oasis.economyx.events.banknote;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.physical.Banknote;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class BanknoteEvent extends EconomyEvent {
    public BanknoteEvent(@NonNull Banknote banknote) {
        this.banknote = banknote;
    }

    @NonNull
    private final Banknote banknote;

    @NonNull
    public Banknote getBanknote() {
        return banknote;
    }
}
