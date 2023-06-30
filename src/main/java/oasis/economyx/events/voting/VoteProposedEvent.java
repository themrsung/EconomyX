package oasis.economyx.events.voting;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.types.governance.Democratic;
import oasis.economyx.interfaces.voting.Vote;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class VoteProposedEvent extends EconomyEvent {
    public VoteProposedEvent(@NonNull Democratic democratic, @NonNull Vote vote) {
        this.democratic = democratic;
        this.vote = vote;
    }

    @NonNull
    private final Democratic democratic;
    @NonNull
    private final Vote vote;

    @NonNull
    public Democratic getDemocratic() {
        return democratic;
    }

    @NonNull
    public Vote getVote() {
        return vote;
    }
}
