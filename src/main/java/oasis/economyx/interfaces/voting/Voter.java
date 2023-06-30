package oasis.economyx.interfaces.voting;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.interfaces.actor.Actor;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A voter represents an actor within a vote.
 */
@JsonSerialize(as = Voter.Votable.class)
@JsonDeserialize(as = Voter.Votable.class)
public interface Voter {
    /**
     * Gets a voter instance.
     *
     * @param actor Actor to cast the votes
     * @param votes Castable votes
     * @return Voter instance
     */
    static Voter get(@NonNull Actor actor, @NonNegative long votes) {
        return new Votable(actor, votes);
    }

    /**
     * Gets the actor capable of casting votes.
     *
     * @return Voter
     */
    Actor getVoter();

    /**
     * Gets the remaining votes this voter can cast.
     *
     * @return Remaining votes
     */
    long getVotes();

    /**
     * Called when this actor has voted.
     * Votes will be reduced by the amount cast.
     *
     * @param votes Votes cast
     */
    void onVoted(long votes);

    class Votable implements Voter {
        public Votable(@NonNull Actor voter, @NonNegative long votes) {
            this.voter = voter;
            this.votes = votes;
        }

        @NonNull
        @JsonProperty
        @JsonIdentityReference
        private final Actor voter;

        @NonNegative
        @JsonProperty
        private long votes;

        @Override
        @NonNull
        @JsonIgnore
        public Actor getVoter() {
            return voter;
        }

        @Override
        @NonNegative
        @JsonIgnore
        public long getVotes() {
            return votes;
        }

        @Override
        @JsonIgnore
        public void onVoted(long votes) {
            this.votes -= votes;
        }
    }
}
