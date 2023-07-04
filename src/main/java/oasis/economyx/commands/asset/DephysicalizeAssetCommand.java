package oasis.economyx.commands.asset;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.asset.AssetDephysicalizedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.PhysicalAsset;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

public final class DephysicalizeAssetCommand extends EconomyCommand {
    public DephysicalizeAssetCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.EMPLOYEE)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        ItemStack item = player.getInventory().getItemInMainHand();
        ItemMeta meta = item.getItemMeta();
        if (meta != null) {
            if (meta.hasLore()) {
                List<String> lore = meta.getLore();
                if (lore != null && lore.size() > 0) {
                    UUID uuid = UUID.fromString(lore.get(0));
                    for (PhysicalAsset pa : getState().getPhysicalizedAssets()) {
                        if (pa.getUniqueId().equals(uuid)) {
                            item.setAmount(0);

                            Bukkit.getPluginManager().callEvent(new AssetDephysicalizedEvent(pa, actor));

                            player.sendRawMessage(Messages.ASSET_DEPHYSICALIZED);
                            return;
                        }
                    }
                }
            }
        }

        player.sendRawMessage(Messages.INVALID_ASSET);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        list.add(Messages.ALL_DONE);
    }
}
