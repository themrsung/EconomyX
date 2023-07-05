package oasis.economyx.classes.actor.institution.tripartite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.institution.Institution;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.governance.Democratic;
import oasis.economyx.interfaces.actor.types.institutional.Legislative;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.interfaces.voting.Voter;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A sovereign can have multiple legislatures (e.g. Senate, Congress, Parliament, etc.)
 */
public final class Legislature extends Institution implements Legislative {
    /**
     * Creates a new legislature
     *
     * @param parent   Parent sovereign (cannot be null)
     * @param uniqueId Unique ID of this legislature
     * @param name     Name of this legislature (not unique)
     * @param currency Currency of this legislature
     */
    public Legislature(@NonNull Sovereign parent, UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(parent, uniqueId, name, currency);

        this.laws = new ArrayList<>();
        this.openVotes = new ArrayList<>();
    }

    public Legislature() {
        super();

        this.laws = new ArrayList<>();
        this.openVotes = new ArrayList<>();
    }

    public Legislature(Legislature other) {
        super(other);

        this.laws = other.laws;
        this.openVotes = other.openVotes;
    }

    @NonNull
    @JsonProperty
    private final List<String> laws;

    @NonNull
    @JsonProperty
    private final List<Vote> openVotes;

    @NonNull
    @Override
    @JsonIgnore
    public List<String> getLaws() {
        return new ArrayList<>(laws);
    }

    @Override
    @JsonIgnore
    public void passLaw(@NonNull String law) {
        laws.add(law);
    }

    @Override
    @JsonIgnore
    public void repealLaw(@NonNull String law) {
        laws.remove(law);
    }

    @NonNull
    @Override
    @JsonIgnore
    public List<Vote> getOpenVotes() {
        return new ArrayList<>(openVotes);
    }

    @Override
    @JsonIgnore
    public void openVote(@NonNull Vote vote) {
        this.openVotes.add(vote);
    }

    @Override
    @JsonIgnore
    public void cleanVotes() {
        openVotes.removeIf(v -> v.getExpiry().isBeforeNow());
        openVotes.removeIf(v -> v.getCandidates().size() == 0);
    }

    @JsonProperty
    private final Type type = Type.LEGISLATURE;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public List<Voter> getVoters(@NonNull EconomyState state) {
        final List<Voter> voters;
        if (getParent() instanceof Democratic d) {
            voters = d.getVoters(state);
        } else {
            voters = new ArrayList<>();
            if (getParent().getRepresentative() != null) {
                voters.add(Voter.get(getParent().getRepresentative(), 1L));
            }
        }

        return voters;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        super.initialize(state);

        for (Vote v : getOpenVotes()) {
            v.initialize(state);
        }
    }
}
