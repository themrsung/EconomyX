package oasis.economyx.commands.management;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.currency.CurrencyIssuedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.institutional.CurrencyIssuer;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class IssueCurrencyCommand extends EconomyCommand {
    public IssueCurrencyCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DE_FACTO_SELF)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (!(actor instanceof CurrencyIssuer ci)) {
            player.sendRawMessage(Messages.ACTOR_NOT_CURRENCY_ISSUER);
            return;
        }

        if (params.length < 1) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        long amount = Inputs.fromNumber(params[0]);
        if (amount <= 0L) {
            player.sendRawMessage(Messages.INVALID_NUMBER);
            return;
        }

        Bukkit.getPluginManager().callEvent(new CurrencyIssuedEvent(ci.getIssuedCurrency(), ci, amount));
        player.sendRawMessage(Messages.CURRENCY_ISSUED);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.add(Messages.INSERT_NUMBER);
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
