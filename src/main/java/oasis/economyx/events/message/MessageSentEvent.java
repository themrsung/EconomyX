package oasis.economyx.events.message;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.types.message.Message;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class MessageSentEvent extends EconomyEvent {
    public MessageSentEvent(@NonNull Message message) {
        this.message = message;
    }

    @NonNull
    private final Message message;

    @NonNull
    public Message getMessage() {
        return message;
    }
}
