package oasis.economyx.events.warfare;

import oasis.economyx.interfaces.actor.types.warfare.Faction;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Revokes hostility. (one-way event)
 */
public final class HostilityRevokedEvent extends HostilityStateChangedEvent {
    public HostilityRevokedEvent(@NonNull Faction initiator, @NonNull Faction hostility) {
        super(initiator, hostility);
    }
}
