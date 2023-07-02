package oasis.economyx.listeners.voting;

import oasis.economyx.EconomyX;
import oasis.economyx.events.voting.VoteProposedEvent;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class VoteProposedListener extends EconomyListener {
    public VoteProposedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVoteProposed(VoteProposedEvent e) {
        if (e.isCancelled()) return;

        e.getDemocratic().openVote(e.getVote());
    }
}
