package oasis.economyx.listeners.player;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.entity.PlayerDeathEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class PlayerDeathHandler extends EconomyListener {
    public PlayerDeathHandler(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent e) {
        Person person = getState().getPerson(e.getEntity().getUniqueId());

        for (AssetStack as : person.getAssets().get()) {
            person.getAssets().remove(as);
            getState().getBurntAssets().add(as);
        }
    }
}
