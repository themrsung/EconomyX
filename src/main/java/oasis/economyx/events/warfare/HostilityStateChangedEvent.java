package oasis.economyx.events.warfare;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.warfare.Faction;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class HostilityStateChangedEvent extends EconomyEvent {
    public HostilityStateChangedEvent(@NonNull Faction initiator, @NonNull Faction hostility) {
        this.initiator = initiator;
        this.hostility = hostility;
    }

    @NonNull
    private final Faction initiator;

    @NonNull
    private final Faction hostility;

    @NonNull
    public Faction getInitiator() {
        return initiator;
    }

    @NonNull
    public Faction getHostility() {
        return hostility;
    }
}
