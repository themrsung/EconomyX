package oasis.economyx.events.sovereign;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class SovereignEvent extends EconomyEvent {
    public SovereignEvent(@NonNull Sovereign sovereign) {
        this.sovereign = sovereign;
    }

    @NonNull
    private final Sovereign sovereign;

    @NonNull
    public Sovereign getSovereign() {
        return sovereign;
    }
}
