package oasis.economyx.commands.sudo;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.commands.address.SetAddressCommand;
import oasis.economyx.commands.balance.BalanceCommand;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.state.EconomyState;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class SudoCommand extends EconomyCommand {
    public SudoCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (params.length < 2) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        final Actor executor = Inputs.searchActor(params[0], getState());
        if (executor == null) {
            player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
            return;
        }

        // Check permissions (Lowest first)
        AccessLevel level = null;

        if (executor instanceof Employer e) {
            level = e.getEmployees().contains(caller) ? AccessLevel.EMPLOYEE : level;
            level = e.getDirectors().contains(caller) ? AccessLevel.DIRECTOR : level;
        }

        if (executor instanceof Representable r) {
            level = Objects.equals(r.getRepresentative(), caller) ? AccessLevel.DE_FACTO_SELF : level;
        }

        if (executor instanceof Person p) {
            level = p.equals(caller) ? AccessLevel.SELF : level;
        }

        if (level == null) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        final Keyword action = Keyword.fromInput(params[1]);
        final String[] argsToPass = params.length > 2 ? Arrays.copyOfRange(params, 1, params.length) : new String[] {};

        if (action == null) {
            player.sendRawMessage(Messages.INVALID_KEYWORD);
            return;
        }

        switch (action) {
            case BALANCE -> {
                BalanceCommand balance = new BalanceCommand(getEX(), getState());
                balance.onEconomyCommand(player, caller, executor, argsToPass, level);
                return;
            }
            case SET_ADDRESS -> {
                SetAddressCommand setAddress = new SetAddressCommand(getEX(), getState());
                setAddress.onEconomyCommand(player, caller, executor, argsToPass, level);
                return;
            }
        }

        player.sendRawMessage(Messages.INVALID_KEYWORD);
        return;
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.ACTOR_NAMES(getState()));
        }else if (params.length < 3) {
            for (Keyword k : Keyword.values()) {
                list.addAll(k.toInput());
            }
        } else {
            final String[] argsToPass = params.length > 3 ? Arrays.copyOfRange(params, 3, params.length) : new String[] {};
            final Keyword action = Keyword.fromInput(params[2]);

            if (action == null) return;

            switch (action) {
                case BALANCE -> {
                    BalanceCommand balance = new BalanceCommand(getEX(), getState());
                    balance.onEconomyComplete(list, argsToPass);
                }
                case SET_ADDRESS -> {
                    SetAddressCommand setAddress = new SetAddressCommand(getEX(), getState());
                    setAddress.onEconomyComplete(list, argsToPass);
                }
            }
        }
    }
}
