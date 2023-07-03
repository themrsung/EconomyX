package oasis.economyx.tasks.message;

import oasis.economyx.EconomyX;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import oasis.economyx.types.message.Message;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class MessageHistoryCleanerTask extends EconomyTask {
    private static final int MESSAGE_LIFETIME_HOURS = 3;
    public MessageHistoryCleanerTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void run() {
        for (Message m : getState().getMessages()) {
            if (m.getTime().plusHours(MESSAGE_LIFETIME_HOURS).isBeforeNow()) {
                getState().removeMessage(m);
            }
        }
    }

    @Override
    public int getInterval() {
        return 200;
    }

    @Override
    public int getDelay() {
        return 20;
    }
}
