package oasis.economyx.tasks.message;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import oasis.economyx.types.message.Message;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class MessengerTask extends EconomyTask {
    public static String formatMessage(@NonNull Message message) {
        final Actor sender = message.getSender();

        if (sender == null) {
            // Server message
            return message.getContent();
        } else {
            return "[" + sender.getName() + " -> " + message.getRecipient().getName() + "] " + message.getContent();
        }
    }

    public MessengerTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void run() {
        for (Message m : getState().getMessages()) {
            if (!m.isShown()) {
                boolean shown = false;

                final Actor recipient = m.getRecipient();
                if (recipient instanceof Person) {
                    Player player = Bukkit.getPlayer(recipient.getUniqueId());
                    if (player != null) {
                        player.sendRawMessage(formatMessage(m));
                        shown = true;
                    }
                }

                if (recipient instanceof Representable r) {
                    if (r.getRepresentative() != null) {
                        Player player = Bukkit.getPlayer(r.getRepresentative().getUniqueId());
                        if (player != null) {
                            player.sendRawMessage(formatMessage(m));
                            shown = true;
                        }
                    }
                }

                if (recipient instanceof Employer e) {
                    for (Person p : e.getEmployees()) {
                        Player player = Bukkit.getPlayer(p.getUniqueId());
                        if (player != null) {
                            player.sendRawMessage(formatMessage(m));
                            shown = true;
                        }
                    }

                    for (Person p : e.getDirectors()) {
                        Player player = Bukkit.getPlayer(p.getUniqueId());
                        if (player != null) {
                            player.sendRawMessage(formatMessage(m));
                            shown = true;
                        }
                    }
                }

                if (shown) m.setShown(true);
            }
        }
    }

    @Override
    public int getInterval() {
        return 1;
    }

    @Override
    public int getDelay() {
        return 100;
    }
}
