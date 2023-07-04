package oasis.economyx.commands.property;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.property.PropertyProtectorChangedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.property.PropertyStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class PropertySetProtectorCommand extends EconomyCommand {

    public PropertySetProtectorCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DIRECTOR)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 1) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        Actor search = params.length > 1 ? Inputs.searchActor(params[1], getState()) : null;
        if (search != null && !(search instanceof PropertyProtector)) {
            player.sendRawMessage(Messages.PROPERTY_PROTECTOR_NOT_FOUND);
            return;
        }

        PropertyProtector protector = search != null ? (PropertyProtector) search : null;

        for (AssetStack as : actor.getAssets().get()) {
            if (as instanceof PropertyStack ps) {
                if (as.getAsset().getName().equalsIgnoreCase(params[0])) {
                    Bukkit.getPluginManager().callEvent(new PropertyProtectorChangedEvent(ps, protector));
                    player.sendRawMessage(Messages.PROPERTY_PROTECTOR_CHANGED);
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
            if (!params[0].equals("")) list.removeIf(s -> s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            list.addAll(Lists.PROPERTY_PROTECTOR_NAMES(getState()));
            if (!params[1].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
