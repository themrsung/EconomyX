package oasis.economyx.listeners.contract;

import oasis.economyx.EconomyX;
import oasis.economyx.events.contract.ContractForgivenEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.contract.ContractStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ContractForgivenListener extends EconomyListener {
    public ContractForgivenListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onContractForgiven(ContractForgivenEvent e) {
        if (e.isCancelled()) return;

        ContractStack contract = e.getContract();
        if (!contract.getMeta().isForgivable() && !e.force()) return;

        Actor holder = e.getHolder();
        holder.getAssets().remove(contract);
    }
}
