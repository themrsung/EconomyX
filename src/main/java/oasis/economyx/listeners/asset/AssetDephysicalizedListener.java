package oasis.economyx.listeners.asset;

import oasis.economyx.EconomyX;
import oasis.economyx.events.asset.AssetDephysicalizedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.PhysicalAsset;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AssetDephysicalizedListener extends EconomyListener {
    public AssetDephysicalizedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAssetDephysicalized(AssetDephysicalizedEvent e) {
        if (e.isCancelled()) return;

        PhysicalAsset asset = e.getAsset();
        Actor holder = e.getDephysicalizer();

        AssetStack dephysicalized = asset.getAsset();

        getState().removePhysicalizedAsset(asset);
        holder.getAssets().add(dephysicalized);


    }
}
