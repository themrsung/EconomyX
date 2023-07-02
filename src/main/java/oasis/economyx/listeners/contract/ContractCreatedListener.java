package oasis.economyx.listeners.contract;

import oasis.economyx.EconomyX;
import oasis.economyx.events.contract.ContractCreatedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.contract.ContractStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ContractCreatedListener extends EconomyListener {
    public ContractCreatedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onContractCreated(ContractCreatedEvent e) {
        if (e.isCancelled()) return;

        Actor holder = e.getHolder();
        ContractStack contract = e.getContract();

        holder.getAssets().add(contract);
    }
}
