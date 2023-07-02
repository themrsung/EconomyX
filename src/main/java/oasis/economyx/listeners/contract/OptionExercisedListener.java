package oasis.economyx.listeners.contract;

import oasis.economyx.EconomyX;
import oasis.economyx.events.contract.OptionExercisedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.contract.option.OptionStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Does not check if option is exercisable. Check before calling.
 */
public final class OptionExercisedListener extends EconomyListener {
    public OptionExercisedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onOptionExercised(OptionExercisedEvent e) {
        if (e.isCancelled()) return;

        OptionStack option = e.getContract();
        Actor holder = e.getHolder();

        for (int i = 0; i < option.getQuantity(); i++) {
            option.getAsset().onExpired(holder);
        }
    }
}
