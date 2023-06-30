package oasis.economyx.listeners.contract;

import oasis.economyx.EconomyX;
import oasis.economyx.events.contract.ContractCreatedEvent;
import oasis.economyx.events.contract.ContractExpiredEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.types.asset.contract.Contract;
import oasis.economyx.types.asset.contract.ContractStack;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ContractExpiredListener extends EconomyListener {
    public ContractExpiredListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onContractCreated(ContractExpiredEvent e) {
        if (e.isCancelled()) return;

        Actor holder = e.getHolder();
        ContractStack contract = e.getContract();
        Contract c = contract.getAsset();

        // Contract expiry has to be called once for every contract instance
        for (int i = 0; i < contract.getQuantity(); i++) {
            c.onExpired(holder);
        }

        // Delete contract
        holder.getAssets().remove(contract);
    }
}
