package oasis.economyx.listener.player;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.person.NaturalPerson;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.listener.EconomyListener;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;

public final class PlayerJoinHandler extends EconomyListener {
    public PlayerJoinHandler(EconomyX EX) {
        super(EX);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        try {
            Person person = getState().getPerson(e.getPlayer().getUniqueId());
            person.setName(e.getPlayer().getName());
        } catch (IllegalArgumentException error) {
            Person person = new NaturalPerson(e.getPlayer().getUniqueId(), e.getPlayer().getName());
            getState().addActor(person);
        }
    }
}
