package oasis.economyx.commands.message;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.message.MessageSentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.message.MessengerTask;
import oasis.economyx.types.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class ReplyCommand extends EconomyCommand {
    public ReplyCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.EMPLOYEE)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 1) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        } else if (params.length >= 2) {
            Actor recipient = Inputs.searchActor(params[0], getState());
            if (recipient != null) {
                List<Message> messages = getState().getMessagesByRecipient(actor);

                for (Message m : messages) {
                    if (Objects.equals(m.getSender(), recipient)) {
                        String content = String.join(" ", Arrays.copyOfRange(params, 1, params.length));
                        Message message = new Message(actor, recipient, content);

                        Bukkit.getPluginManager().callEvent(new MessageSentEvent(message));

                        player.sendRawMessage(MessengerTask.formatMessage(message));
                        return;
                    }
                }
            }
        }

        List<Message> messages = getState().getMessagesByRecipient(actor);

        for (Message m : messages) {
            if (m.getSender() != null) {
                Actor recipient = m.getSender();

                String content = String.join(" ", Arrays.copyOfRange(params, 0, params.length));
                Message message = new Message(actor, recipient, content);
                Bukkit.getPluginManager().callEvent(new MessageSentEvent(message));

                player.sendRawMessage(MessengerTask.formatMessage(message));
                return;
            }
        }

        player.sendRawMessage(Messages.NO_MESSAGES_RECEIVED);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.ACTOR_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else {
            list.add(Messages.INSERT_MESSAGE);
        }
    }
}
