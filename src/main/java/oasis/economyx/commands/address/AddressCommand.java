package oasis.economyx.commands.address;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.address.Address;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class AddressCommand extends EconomyCommand {
    public AddressCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        Actor query = params.length > 0 ? Inputs.searchActor(params[0], getState()) : actor;
        if (query == null) {
            player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
            return;
        }

        AccessLevel level = AccessLevel.getPermission(query, actor);
        if (!level.isAtLeast(AccessLevel.EMPLOYEE)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        Address address = query.getAddress();
        if (address == null) {
            player.sendRawMessage(Messages.ADDRESS_NOT_FOUND);
            return;
        }

        player.sendRawMessage(Messages.ADDRESS_OF_ACTOR(query, address));
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.ACTOR_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
