package oasis.economyx.tasks.voting;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.governance.Democratic;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class VoteProcessTask extends EconomyTask {
    public VoteProcessTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void run() {
        for (Democratic d : getState().getDemocratics()) {
            d.cleanVotes();
            for (Vote v : d.getOpenVotes()) v.processVotes();
        }
    }

    @Override
    public int getDelay() {
        return 20;
    }

    @Override
    public int getInterval() {
        return 2;
    }
}
