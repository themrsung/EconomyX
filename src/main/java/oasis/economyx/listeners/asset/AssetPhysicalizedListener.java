package oasis.economyx.listeners.asset;

import oasis.economyx.EconomyX;
import oasis.economyx.events.asset.AssetPhysicalizedEvent;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.PhysicalAsset;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class AssetPhysicalizedListener extends EconomyListener {
    public AssetPhysicalizedListener(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onAssetPhysicalized(AssetPhysicalizedEvent e) {
        if (e.isCancelled()) return;

        PhysicalAsset physicalAsset = PhysicalAsset.physicalizeAsset(e.getAsset());
        ItemStack item = physicalAsset.getPhysicalItem();

        e.getHolder().getAssets().remove(e.getAsset());
        getState().addPhysicalizedAsset(physicalAsset);

        e.getPhysicalizer().getInventory().addItem(item);
    }
}
