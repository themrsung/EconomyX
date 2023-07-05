package oasis.economyx.commands.join;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.sovereign.SovereignMemberAddedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class JoinCommand extends EconomyCommand {
    public JoinCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
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

        Sovereign sovereign = Inputs.searchSovereign(params[0], getState());
        if (sovereign == null) {
            player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
            return;
        }

        if (sovereign.getMembers().contains(actor)) {
            player.sendRawMessage(Messages.ALREADY_MEMBER_OF_SOVEREIGN);
            return;
        }

        Bukkit.getPluginManager().callEvent(new SovereignMemberAddedEvent(sovereign, actor));
        player.sendRawMessage(Messages.JOINED_SOVEREIGN);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.SOVEREIGN_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
