package oasis.economyx.events.actor;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.address.Address;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

public final class ActorAddressChangedEvent extends ActorEvent {
    public ActorAddressChangedEvent(@NonNull Actor actor, @Nullable Address address) {
        super(actor);
        this.address = address;
    }

    @Nullable
    private final Address address;

    @Nullable
    public Address getAddress() {
        return address;
    }
}
