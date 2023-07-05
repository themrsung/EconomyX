package oasis.economyx.commands.address;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.actor.ActorAddressChangedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.address.Address;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class SetAddressCommand extends EconomyCommand {
    public SetAddressCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DE_FACTO_SELF)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        final Location location = player.getLocation();
        final Address address = Address.fromLocation(location);

        // No need to check for existing property claims; Setting an address does not provide protection.

        Bukkit.getPluginManager().callEvent(new ActorAddressChangedEvent(
                actor,
                address
        ));
        player.sendRawMessage(Messages.ADDRESS_CHANGED);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        list.add(Messages.ALL_DONE);
    }
}
