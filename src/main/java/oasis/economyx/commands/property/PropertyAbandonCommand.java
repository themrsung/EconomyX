package oasis.economyx.commands.property;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.property.PropertyAbandonedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.property.PropertyStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class PropertyAbandonCommand extends EconomyCommand {

    public PropertyAbandonCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DE_FACTO_SELF)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 1) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        for (AssetStack as : actor.getAssets().get()) {
            if (as instanceof PropertyStack ps) {
                if (ps.getAsset().getName().equalsIgnoreCase(params[0])) {
                    Bukkit.getPluginManager().callEvent(new PropertyAbandonedEvent(ps, actor));
                    player.sendRawMessage(Messages.PROPERTY_ABANDONED);
                    return;
                }
            }
        }

        player.sendRawMessage(Messages.ASSET_NOT_FOUND);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.ASSET_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
