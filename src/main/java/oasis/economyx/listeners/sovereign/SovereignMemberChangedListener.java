package oasis.economyx.listeners.sovereign;

import oasis.economyx.EconomyX;
import oasis.economyx.events.sovereign.SovereignMemberAddedEvent;
import oasis.economyx.events.sovereign.SovereignMemberRemovedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.sovereign.Federal;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class SovereignMemberChangedListener extends EconomyListener {
    public SovereignMemberChangedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSovereignMemberAdded(SovereignMemberAddedEvent e) {
        if (e.isCancelled()) return;

        Actor member = e.getMember();
        if (member instanceof Person p) {
            e.getSovereign().addCitizen(p);
        } else if (member instanceof Corporation c) {
            e.getSovereign().addCorporation(c);
        } else if (member instanceof Sovereign s && e.getSovereign() instanceof Federal f) {
            f.addMemberState(s);
        }
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onSovereignMemberRemoved(SovereignMemberRemovedEvent e) {
        if (e.isCancelled()) return;

        Actor member = e.getMember();
        if (member instanceof Person p) {
            e.getSovereign().removeCitizen(p);
        } else if (member instanceof Corporation c) {
            e.getSovereign().removeCorporation(c);
        } else if (member instanceof Sovereign s && e.getSovereign() instanceof Federal f) {
            f.removeMemberState(s);
        }
    }
}
