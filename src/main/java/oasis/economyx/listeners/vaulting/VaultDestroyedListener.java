package oasis.economyx.listeners.vaulting;

import oasis.economyx.EconomyX;
import oasis.economyx.interfaces.actor.types.services.VaultKeeper;
import oasis.economyx.interfaces.vaulting.VaultBlock;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.types.asset.commodity.CommodityStack;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class VaultDestroyedListener extends EconomyListener {
    public VaultDestroyedListener(@NonNull EconomyX EX) {
        super(EX);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onVaultDestroyed(BlockBreakEvent e) {
        if (e.isCancelled()) return;

        Block block = e.getBlock();
        Location location = block.getLocation();

        for (VaultBlock vb : getState().getVaultBlocks()) {
            if (vb.getLocation().equals(location)) {
                // Vault found
                VaultKeeper keeper = vb.getKeeper();
                if (keeper != null) keeper.removeVault(vb);
                World world = block.getWorld();

                for (CommodityStack cs : vb.getItems()) {
                    ItemStack stack = new ItemStack(cs.getAsset().getItemType(), (int) cs.getQuantity());
                    world.dropItem(location, stack);
                }

                break;
            }
        }
    }

}
