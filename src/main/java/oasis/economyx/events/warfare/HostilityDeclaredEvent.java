package oasis.economyx.events.warfare;

import oasis.economyx.interfaces.actor.types.warfare.Faction;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Declaration of war. (two-way event)
 */
public final class HostilityDeclaredEvent extends HostilityStateChangedEvent {
    public HostilityDeclaredEvent(@NonNull Faction initiator, @NonNull Faction hostility) {
        super(initiator, hostility);
    }
}
