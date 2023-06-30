package oasis.economyx.interfaces.voting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * A candidate represents an option the voter can choose in a vote.
 */
@JsonSerialize(as = Candidate.Candidacy.class)
@JsonDeserialize(as = Candidate.Candidacy.class)
public interface Candidate {
    /**
     * Gets a candidate instance.
     *
     * @param agenda   Agenda of this candidate.
     * @param onChosen Action to execute when this candidate is chosen.
     * @return Instance
     */
    static Candidate get(@NonNull String agenda, @NonNull Agenda onChosen) {
        return new Candidacy(onChosen);
    }

    /**
     * Gets the cumulative acquired votes of this candidate.
     *
     * @return Acquired votes
     */
    @NonNegative
    long getAcquiredVotes();

    /**
     * Called when votes are acquired.
     *
     * @param votes Number of votes acquired.
     */
    void onVotesAcquired(@NonNegative long votes);

    /**
     * Called when this candidate is declared the winner.
     */
    Agenda getAgenda();

    final class Candidacy implements Candidate {
        @JsonProperty
        private @NonNegative long acquiredVotes;
        @JsonProperty
        private final @NonNull Agenda agenda;

        Candidacy(@NonNull @JsonProperty Agenda agenda) {
            this.acquiredVotes = 0L;
            this.agenda = agenda;
        }

        @Override
        @NonNegative
        @JsonIgnore
        public long getAcquiredVotes() {
            return acquiredVotes;
        }

        @Override
        @JsonIgnore
        public void onVotesAcquired(@NonNegative long votes) {
            acquiredVotes += votes;
        }

        @Override
        @NonNull
        @JsonProperty
        public Agenda getAgenda() {
            return agenda;
        }

        /**
         * Used for IO
         */
        @ConstructorProperties({"agenda", "acquiredVotes", "getAgenda"})
        private Candidacy() {
            this.acquiredVotes = 0L;
            this.agenda = null;
        }
    }
}
