package oasis.economyx.interfaces.actor.types.governance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * A democratic actor can host votes.
 */
public interface Democratic extends Representable {
    /**
     * Gets all open votes.
     *
     * @return A copied list of votes
     */
    @NonNull
    @JsonIgnore
    List<Vote> getOpenVotes();

    /**
     * Opens a new voting.
     *
     * @param vote Vote to open.
     */
    @JsonIgnore
    void openVote(@NonNull Vote vote);

    /**
     * Called regularly. Cleans votes marked to be deleted and expired votes.
     */
    @JsonIgnore
    void cleanVotes();

    /**
     * Gets all applicable voters at this time.
     * @return Voters
     */
    @JsonIgnore
    List<Voter> getVoters(@NonNull EconomyState state);
}
