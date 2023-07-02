package oasis.economyx.listeners.voting;

import oasis.economyx.EconomyX;
import oasis.economyx.events.voting.VoteCastEvent;
import oasis.economyx.interfaces.voting.Candidate;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class VoteCastListener extends EconomyListener {
    public VoteCastListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVoteCast(VoteCastEvent e) throws IllegalArgumentException {
        if (e.isCancelled()) return;

        Vote vote = e.getVote();

        Voter voter = e.getVoter();
        Candidate candidate = e.getCandidate();
        long votes = e.getVotes();

        vote.vote(voter, candidate, votes);
    }
}
