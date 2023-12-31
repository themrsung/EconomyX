package oasis.economyx.tasks.expiry;

import oasis.economyx.EconomyX;
import oasis.economyx.events.contract.ContractExpiredEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.state.EconomyState;
import oasis.economyx.tasks.EconomyTask;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.ContractStack;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ContractExpiryTask extends EconomyTask {
    public ContractExpiryTask(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void run() {
        for (Actor a : getState().getActors()) {
            for (AssetStack as : a.getAssets().get()) {
                if (as instanceof ContractStack cs) {
                    Bukkit.getPluginManager().callEvent(new ContractExpiredEvent(
                            cs,
                            a
                    ));
                }
            }
        }
    }

    @Override
    public int getDelay() {
        return 20;
    }

    @Override
    public int getInterval() {
        return 60 * 60 * 20;
    }
}
