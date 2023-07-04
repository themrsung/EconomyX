package oasis.economyx.interfaces.voting;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * A candidate represents an option the voter can choose in a voting.
 */
@JsonSerialize(as = Candidate.Candidacy.class)
@JsonDeserialize(as = Candidate.Candidacy.class)
public interface Candidate extends References {
    /**
     * Gets a candidate instance.
     *
     * @param name   Agenda of this candidate.
     * @param agenda Action to execute when this candidate is chosen.
     * @return Instance
     */
    static Candidate get(@NonNull String name, @NonNull Agenda agenda) {
        return new Candidacy(name, agenda);
    }

    /**
     * Gets the semantic name of this candidate.
     * @return Name
     */
    @NonNull
    @JsonIgnore
    String getName();

    /**
     * Gets the cumulative acquired votes of this candidate.
     *
     * @return Acquired votes
     */
    @NonNegative
    @JsonIgnore
    long getAcquiredVotes();

    /**
     * Called when votes are acquired.
     *
     * @param votes Number of votes acquired.
     */
    @JsonIgnore
    void onVotesAcquired(@NonNegative long votes);

    /**
     * Called when this candidate is declared the winner.
     */
    @JsonIgnore
    Agenda getAgenda();

    @Override
    default void initialize(@NonNull EconomyState state) {
        if (getAgenda() instanceof References r) {
            r.initialize(state);
        }
    }

    final class Candidacy implements Candidate {
        @JsonProperty
        @NonNull
        private final String name;
        @JsonProperty
        private @NonNegative long acquiredVotes;
        @JsonProperty
        private final @NonNull Agenda agenda;

        Candidacy(@NonNull String name, @NonNull Agenda agenda) {
            this.name = name;
            this.acquiredVotes = 0L;
            this.agenda = agenda;
        }

        @NonNull
        @Override
        @JsonIgnore
        public String getName() {
            return name;
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
        @ConstructorProperties({"name", "agenda", "acquiredVotes"})
        private Candidacy() {
            this.name = null;
            this.acquiredVotes = 0L;
            this.agenda = null;
        }
    }
}
