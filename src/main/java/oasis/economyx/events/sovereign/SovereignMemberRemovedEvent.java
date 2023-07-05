package oasis.economyx.events.sovereign;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class SovereignMemberRemovedEvent extends SovereignEvent {
    public SovereignMemberRemovedEvent(@NonNull Sovereign sovereign, @NonNull Actor member) {
        super(sovereign);
        this.member = member;
    }

    @NonNull
    private final Actor member;

    @NonNull
    public Actor getMember() {
        return member;
    }
}
