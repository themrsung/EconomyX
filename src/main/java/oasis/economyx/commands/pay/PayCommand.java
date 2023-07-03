package oasis.economyx.commands.pay;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class PayCommand extends EconomyCommand {
    public PayCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (params.length < 3) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        Actor recipient = Inputs.searchActor(params[0], getState());
        Cash currency = Inputs.searchCurrency(params[1], getState());
        long money = Inputs.fromNumber(params[2]);

        if (recipient == null) {
            player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
            return;
        }

        if (currency == null) {
            player.sendRawMessage(Messages.INVALID_CURRENCY);
            return;
        }

        if (money < 0L) {
            player.sendRawMessage(Messages.INVALID_NUMBER);
            return;
        }

        CashStack payment = new CashStack(currency, money);
        if (!actor.getPayableAssets(getState()).contains(payment)) {
            player.sendRawMessage(Messages.INSUFFICIENT_CASH);
            return;
        }

        Bukkit.getPluginManager().callEvent(new PaymentEvent(actor, recipient, payment, PaymentEvent.Cause.PAY_COMMAND));
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.ACTOR_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            list.addAll(Lists.CURRENCY_NAMES(getState()));
            if (!params[1].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
        } else if (params.length < 4) {
            list.add(Messages.INSERT_NUMBER);
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
