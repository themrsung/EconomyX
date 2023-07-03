package oasis.economyx.interfaces.voting;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.classes.voting.common.DummyAgenda;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.joda.time.DateTime;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A vote has multiple candidates to be selected by voters.
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonSerialize(as = Vote.Ballot.class)
@JsonDeserialize(as = Vote.Ballot.class)
public interface Vote extends References { // TODO Make a builder; Constructing votes is VERY tedious.
    /**
     * Gets a multiple selection vote.
     *
     * @param uniqueId              Unique ID of this vote
     * @param name                  Name of this vote
     * @param candidates            Candidates of this vote
     * @param voters                Voters of this vote
     * @param expiry                Expiry of this vote
     * @param requiredApprovalRatio Required approval ratio
     * @param requiredVotesToPass   Required votes to pass
     * @return New vote instance
     */
    static Vote getMultipleSelectionVote(UUID uniqueId, String name, @NonNull List<Candidate> candidates, @NonNull List<Voter> voters, @NonNull DateTime expiry, @NonNegative float requiredApprovalRatio, @NonNegative long requiredVotesToPass) {
        long votes = 0L;

        for (Voter v : voters) {
            votes += v.getVotes();
        }

        return new Ballot(uniqueId, name, candidates, voters, votes, expiry, requiredApprovalRatio, requiredVotesToPass);
    }

    /**
     * Gets a boolean selection vote. (Yes or No)
     *
     * @param uniqueId              Unique ID of this vote
     * @param name                  Name of this vote
     * @param voters                Voters of this vote
     * @param agenda                Action to execute on passed
     * @param expiry                Expiry of this vote
     * @param requiredApprovalRatio Required approval ratio
     * @param requiredVotesToPass   Required votes to pass
     * @return New vote instance
     */
    static Vote getBooleanVote(UUID uniqueId, String name, @NonNull List<Voter> voters, @NonNull Agenda agenda, @NonNull DateTime expiry, @NonNegative float requiredApprovalRatio, @NonNegative long requiredVotesToPass) {
        long votes = 0L;

        for (Voter v : voters) {
            votes += v.getVotes();
        }

        List<Candidate> candidates = new ArrayList<>();
        candidates.add(Candidate.get("Yes", agenda));
        candidates.add(Candidate.get("No", new DummyAgenda("Do nothing.")));

        return new Ballot(uniqueId, name, candidates, voters, votes, expiry, requiredApprovalRatio, requiredVotesToPass);
    }

    /**
     * Gets the unique ID of this vote.
     *
     * @return Unique ID
     */
    @NonNull
    @JsonIgnore
    UUID getUniqueId();

    /**
     * Gets the name of this vote.
     *
     * @return Name
     */
    @NonNull
    @JsonIgnore
    String getName();

    /**
     * Gets the candidates of this vote.
     *
     * @return A copied list of candidates
     */
    @NonNull
    @JsonIgnore
    List<Candidate> getCandidates();

    /**
     * Gets the voter of this vote.
     *
     * @return A copied list of voters
     */
    @NonNull
    @JsonIgnore
    List<Voter> getVoters();

    /**
     * Gets the total castable votes of this vote.
     *
     * @return Total castable votes
     */
    @NonNegative
    @JsonIgnore
    long getTotalCastableVotes();

    /**
     * Gets the total cast votes so far.
     *
     * @return Total cast votes
     */
    @NonNegative
    @JsonIgnore
    long getCastVotes();

    /**
     * Gets the expiry of this vote.
     * Votes will automatically fail on expiry.
     *
     * @return Expiry
     */
    @NonNull
    @JsonIgnore
    DateTime getExpiry();

    /**
     * Gets the required approval ratio of this vote.
     * (e.g. 50% -> A candidate requires at least 50% of cast votes)
     *
     * @return Required approval ratio
     */
    @NonNegative
    @JsonIgnore
    float getRequiredApprovalRatio();

    /**
     * Gets the required amount of votes to pass.
     * (e.g. 500 -> A candidate requires at least 500 votes to pass)
     *
     * @return Required votes to pass
     */
    @NonNegative
    @JsonIgnore
    long getRequiredVotesToPass();

    /**
     * Casts a vote.
     *
     * @param voter     Voter casting the vote.
     * @param candidate Candidate this voter has chosen.
     * @param votes     Number of votes to cast.
     * @throws IllegalArgumentException When candidate is invalid, or voter has insufficient votes.
     */
    @JsonIgnore
    void vote(@NonNull Voter voter, @NonNull Candidate candidate, @NonNegative long votes) throws IllegalArgumentException;

    /**
     * Handles internal processing of a vote.
     */
    @JsonIgnore
    void processVotes();

    @Override
    default void initialize(@NonNull EconomyState state) {
        for (Candidate c : getCandidates()) {
            c.initialize(state);
        }
    }

    // No, I don't do the Impl thing.
    // You are free to modify this code, but do NOT call this class VoteImpl.
    // I will put you on my blacklist and NEVER authorize a PR from you.
    class Ballot implements Vote {
        Ballot(@NonNull UUID uniqueId, @NonNull String name, @NonNull List<Candidate> candidates, @NonNull List<Voter> voters, long totalCastableVotes, DateTime expiry, float requiredApprovalRatio, long requiredVotesToPass) {
            this.uniqueId = uniqueId;
            this.name = name;
            this.candidates = candidates;
            this.voters = voters;
            this.totalCastableVotes = totalCastableVotes;
            this.castVotes = 0L;
            this.expiry = expiry;
            this.requiredApprovalRatio = requiredApprovalRatio;
            this.requiredVotesToPass = requiredVotesToPass;
        }

        @Override
        public void vote(@NonNull Voter voter, @NonNull Candidate candidate, @NonNegative long votes) throws IllegalArgumentException {
            if (voter.getVotes() <= votes || !this.getCandidates().contains(candidate))
                throw new IllegalArgumentException();

            castVotes += votes;
            candidate.onVotesAcquired(votes);
            voter.onVoted(votes);
        }

        @NonNull
        @JsonProperty
        private final UUID uniqueId;
        @NonNull
        @JsonProperty
        private final String name;

        @NonNull
        @JsonProperty
        private final List<Candidate> candidates;
        @NonNull
        @JsonProperty
        private final List<Voter> voters;
        @NonNegative
        @JsonProperty
        private final long totalCastableVotes;
        @NonNegative
        @JsonProperty
        private long castVotes;

        @NonNull
        @JsonProperty
        private final DateTime expiry;
        @NonNegative
        @JsonProperty
        private final float requiredApprovalRatio;
        @NonNegative
        @JsonProperty
        private final long requiredVotesToPass;

        @NonNull
        @Override
        @JsonIgnore
        public UUID getUniqueId() {
            return uniqueId;
        }

        @NonNull
        @Override
        @JsonIgnore
        public String getName() {
            return name;
        }

        @Override
        @JsonIgnore
        public @NonNull List<Candidate> getCandidates() {
            return new ArrayList<>(candidates);
        }

        @Override
        @JsonIgnore
        public @NonNull List<Voter> getVoters() {
            return new ArrayList<>(voters);
        }

        @Override
        @JsonIgnore
        public long getTotalCastableVotes() {
            return totalCastableVotes;
        }

        @Override
        @JsonIgnore
        public long getCastVotes() {
            return castVotes;
        }

        @Override
        @NonNull
        @JsonIgnore
        public DateTime getExpiry() {
            return expiry;
        }

        @Override
        @JsonIgnore
        public float getRequiredApprovalRatio() {
            return requiredApprovalRatio;
        }

        @Override
        @JsonIgnore
        public long getRequiredVotesToPass() {
            return requiredVotesToPass;
        }

        @Override
        @JsonIgnore
        public void processVotes() {
            candidates.sort((c1, c2) -> Long.compare(c2.getAcquiredVotes(), c1.getAcquiredVotes()));
            if (candidates.size() == 0) throw new RuntimeException();

            Candidate c = candidates.get(0);

            try {
                float approvalRatio = (float) c.getAcquiredVotes() / castVotes;
                if (approvalRatio >= getRequiredApprovalRatio() && c.getAcquiredVotes() >= getRequiredVotesToPass()) {
                    // Vote passed
                    c.getAgenda().run();

                    // Mark vote to be removed by its holder
                    candidates.clear();
                    voters.clear();
                }
            } catch (ArithmeticException e) {
                // There are no cast votes
            }
        }

        /**
         * Used for IO
         */
        @ConstructorProperties({"uniqueId", "name", "candidates", "voters", "totalCastableVotes", "expiry", "requiredApprovalRatio", "requiredVotesToPass"})
        private Ballot() {
            this.uniqueId = null;
            this.name = null;
            this.candidates = new ArrayList<>();
            this.voters = new ArrayList<>();
            this.totalCastableVotes = 0L;
            this.expiry = null;
            this.requiredApprovalRatio = 0f;
            this.requiredVotesToPass = 0L;
        }
    }
}
