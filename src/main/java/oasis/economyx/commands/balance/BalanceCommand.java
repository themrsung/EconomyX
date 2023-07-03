package oasis.economyx.commands.balance;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class BalanceCommand extends EconomyCommand {
    public BalanceCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.EMPLOYEE)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }
        for (AssetStack as : actor.getAssets().get()) {
            if (as instanceof CashStack cs) {
                player.sendRawMessage(Messages.CASH_BALANCE(cs));
            }
        }
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.CURRENCY_NAMES(getState()));
        }
    }
}
