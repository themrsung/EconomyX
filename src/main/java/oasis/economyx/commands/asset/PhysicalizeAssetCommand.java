package oasis.economyx.commands.asset;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.asset.AssetPhysicalizedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.PhysicalAsset;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class PhysicalizeAssetCommand extends EconomyCommand {
    public PhysicalizeAssetCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DE_FACTO_SELF)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 2) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        long quantity = Inputs.fromNumber(params[1]);
        if (quantity < 0L) {
            player.sendRawMessage(Messages.INVALID_NUMBER);
            return;
        }

        for (AssetStack as : actor.getAssets().get()) {
            if (as.getAsset().getName().equalsIgnoreCase(params[0])) {
                if (quantity > as.getQuantity()) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ASSETS);
                    return;
                }

                AssetStack asset = as.copy();
                asset.setQuantity(quantity);

                Bukkit.getPluginManager().callEvent(new AssetPhysicalizedEvent(actor, asset, player));

                player.sendRawMessage(Messages.ASSET_PHYSICALIZED);
                return;
            }
        }

        player.sendRawMessage(Messages.ASSET_NOT_FOUND);
        return;
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.ASSET_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            list.add(Messages.INSERT_NUMBER);
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
