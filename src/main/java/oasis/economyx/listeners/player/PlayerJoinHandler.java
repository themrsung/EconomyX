package oasis.economyx.listeners.player;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.person.NaturalPerson;
import oasis.economyx.events.actor.ActorCreatedEvent;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PlayerJoinHandler extends EconomyListener {
    public PlayerJoinHandler(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) {
        try {
            Person person = getState().getPerson(e.getPlayer().getUniqueId());
            person.setName(e.getPlayer().getName());
        } catch (IllegalArgumentException error) {
            Person person = new NaturalPerson(e.getPlayer().getUniqueId(), e.getPlayer().getName());
            Bukkit.getPluginManager().callEvent(new ActorCreatedEvent(
                    person,
                    null,
                    ActorCreatedEvent.Type.PLAYER_PROFILE_CREATED
            ));
        }
    }
}
