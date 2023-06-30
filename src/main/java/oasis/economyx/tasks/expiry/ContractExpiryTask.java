package oasis.economyx.tasks.expiry;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.tasks.EconomyTask;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.contract.Contract;

public final class ContractExpiryTask extends EconomyTask {
    public ContractExpiryTask(EconomyX EX) {
        super(EX);
    }

    @Override
    public void run() {
        for (Actor a : getState().getActors()) {
            for (AssetStack as : a.getAssets().get()) {
                if (as.getAsset() instanceof Contract c) {

                    // Contract expiry has to be called once for every contract instance
                    for (int i = 0; i < as.getQuantity(); i++) {
                        c.onExpired(a);
                    }

                    // Delete contract
                    a.getAssets().remove(as);
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
