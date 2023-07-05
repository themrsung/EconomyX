package oasis.economyx.commands.warfare;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.warfare.HostilityDeclaredEvent;
import oasis.economyx.events.warfare.HostilityRevokedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.warfare.Faction;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.List;

public final class HostilityCommand extends EconomyCommand {
    public HostilityCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!(actor instanceof Faction initiator)) {
            player.sendRawMessage(Messages.ACTOR_NOT_FACTION);
            return;
        }

        if (params.length < 1) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        Action action = Action.fromInput(params[0]);
        if (action == null) {
            player.sendRawMessage(Messages.INVALID_KEYWORD);
            return;
        }

        switch (action) {
            case DECLARE_WAR, REVOKE_HOSTILITY -> {
                if (!permission.isAtLeast(AccessLevel.DE_FACTO_SELF)) {
                    player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
                    return;
                }

                if (params.length < 2) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                    return;
                }

                Faction faction = Inputs.searchFaction(params[1], getState());
                if (faction == null) {
                    player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
                    return;
                }

                switch (action) {
                    case DECLARE_WAR -> {
                        if (initiator.equals(faction)) {
                            player.sendRawMessage(Messages.CANNOT_DECLARE_WAR_ON_ONESELF);
                            return;
                        }

                        Bukkit.getPluginManager().callEvent(new HostilityDeclaredEvent(initiator, faction));
                        player.sendRawMessage(Messages.HOSTILITY_DECLARED);
                    }
                    case REVOKE_HOSTILITY -> {
                        Bukkit.getPluginManager().callEvent(new HostilityRevokedEvent(initiator, faction));
                        player.sendRawMessage(Messages.HOSTILITY_REVOKED);
                    }
                    default -> {
                        player.sendRawMessage(Messages.UNKNOWN_ERROR);
                    }
                }
            }
            case LIST_ENEMIES -> {
                for (Faction f : initiator.getEnemies(getState())) {
                    player.sendRawMessage(Messages.HOSTILITY_INFORMATION(initiator, f));
                }
            }
        }
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            for (Action a : Action.values()) list.addAll(a.toInput());
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            list.addAll(Lists.FACTION_NAMES(getState()));
            if (!params[1].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
        }
    }

    private enum Action {
        DECLARE_WAR,
        REVOKE_HOSTILITY,
        LIST_ENEMIES;

        private static final List<String> K_DECLARE_WAR = Arrays.asList("dw", "declare", "declarewar", "전쟁선포");
        private static final List<String> K_REVOKE_HOSTILITY = Arrays.asList("rh", "revoke", "revokehostility", "전쟁종료");
        private static final List<String> K_LIST_ENEMIES = Arrays.asList("ls", "list", "목록", "적목록");

        @Nullable
        public static Action fromInput(@NonNull String input) {
            if (K_DECLARE_WAR.contains(input.toLowerCase())) return DECLARE_WAR;
            if (K_REVOKE_HOSTILITY.contains(input.toLowerCase())) return REVOKE_HOSTILITY;
            if (K_LIST_ENEMIES.contains(input.toLowerCase())) return LIST_ENEMIES;
            return null;
        }

        @NonNull
        public List<String> toInput() {
            return switch (this) {
                case DECLARE_WAR -> K_DECLARE_WAR;
                case REVOKE_HOSTILITY -> K_REVOKE_HOSTILITY;
                case LIST_ENEMIES -> K_LIST_ENEMIES;
            };
        }
    }
}
