package oasis.economyx.tasks.voting;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.governance.Democratic;
import oasis.economyx.interfaces.voting.Vote;
import oasis.economyx.tasks.EconomyTask;

public final class VoteProcessTask extends EconomyTask {
    public VoteProcessTask(EconomyX EX) {
        super(EX);
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
