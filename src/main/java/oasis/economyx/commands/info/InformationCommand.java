package oasis.economyx.commands.info;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class InformationCommand extends EconomyCommand {
    public InformationCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (params.length < 1) {
            showActorInformation(player, actor, permission);
        } else {
            Actor info = Inputs.searchActor(params[0], getState());
            if (info == null) {
                player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
                return;
            }

            AccessLevel perm = AccessLevel.getPermission(info, actor);

            showActorInformation(player, info, perm);
        }
    }

    private void showActorInformation(@NonNull Player player, @NonNull Actor info, @NonNull AccessLevel permission) {
        for (String s : Messages.ACTOR_INFORMATION_OUTSIDER(info)) {
            player.sendRawMessage(s);
        }
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
