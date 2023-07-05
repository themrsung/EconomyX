package oasis.economyx.commands.management;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.personal.representable.RepresentativeHiredEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.List;

public final class ManageInstitutionCommand extends EconomyCommand {
    public ManageInstitutionCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
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

        if (!(actor instanceof Sovereign s)) {
            player.sendRawMessage(Messages.ACTOR_NOT_SOVEREIGN);
            return;
        }

        Institutional institution = Inputs.searchInstitution(params[0], getState());
        if (institution == null) {
            player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
            return;
        }

        if (!s.getInstitutions().contains(institution)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        Action action = Action.fromInput(params[1]);
        if (action == null) {
            player.sendRawMessage(Messages.INVALID_KEYWORD);
            return;
        }

        switch (action) {
            case SET_REPRESENTATIVE -> {
                if (params.length < 3) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                    return;
                }

                Person rep = Inputs.searchPerson(params[2], getState());
                if (rep == null) {
                    player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
                    return;
                }

                if (institution.getType() == Actor.Type.LEGISLATURE) {
                    player.sendRawMessage(Messages.REPRESENTATIVE_OF_LEGISLATURE_MUST_BE_ELECTED);
                    return;
                }

                Bukkit.getPluginManager().callEvent(new RepresentativeHiredEvent(rep, institution));
                player.sendRawMessage(Messages.REPRESENTATIVE_CHANGED);
            }
            default -> {
                player.sendRawMessage(Messages.UNKNOWN_ERROR);
            }
        }
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.INSTITUTION_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            for (Action a : Action.values()) list.addAll(a.toInput());
            if (!params[1].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
        } else {
            Action action = Action.fromInput(params[1]);
            if (action != null) {
                switch (action) {
                    case SET_REPRESENTATIVE -> {
                        if (params.length < 4) {
                            list.addAll(Lists.ACTOR_NAMES(getState()));
                            if (!params[2].equals(""))
                                list.removeIf(s -> !s.toLowerCase().startsWith(params[2].toLowerCase()));
                        } else {
                            list.add(Messages.ALL_DONE);
                        }
                    }
                }
            }
        }
    }

    private enum Action {
        SET_REPRESENTATIVE;

        private static final List<String> K_SET_REPRESENTATIVE = Arrays.asList("sr", "setrep", "setrepresentative", "대표설정", "대표선임");

        @Nullable
        public static Action fromInput(@NonNull String input) {
            if (K_SET_REPRESENTATIVE.contains(input.toLowerCase())) return SET_REPRESENTATIVE;
            return null;
        }

        @NonNull
        public List<String> toInput() {
            return switch (this) {
                case SET_REPRESENTATIVE -> K_SET_REPRESENTATIVE;
            };
        }
    }
}
