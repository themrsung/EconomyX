package oasis.economyx.events.voting;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.voting.Candidate;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a vote cast to a candidate.
 */
public final class VoteCastEvent extends EconomyEvent {
    /**
     * Creates a new vote cast event.
     *
     * @param vote      Ongoing vote
     * @param candidate Candidate to vote in
     * @param votes     How many votes to exercise
     */
    public VoteCastEvent(@NonNull Vote vote, @NonNull Voter voter, @NonNull Candidate candidate, @NonNegative long votes) {
        this.vote = vote;
        this.voter = voter;
        this.candidate = candidate;
        this.votes = votes;
    }

    @NonNull
    private final Vote vote;

    @NonNull
    private final Voter voter;

    @NonNull
    private final Candidate candidate;

    @NonNegative
    private final long votes;

    @NonNull
    public Vote getVote() {
        return vote;
    }

    @NonNull
    public Voter getVoter() {
        return voter;
    }

    @NonNull
    public Candidate getCandidate() {
        return candidate;
    }

    public long getVotes() {
        return votes;
    }
}
