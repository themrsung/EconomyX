package oasis.economyx.commands.asset;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.AssetStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class SendAssetCommand extends EconomyCommand {
    public SendAssetCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DE_FACTO_SELF)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 3) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        Actor recipient = Inputs.searchActor(params[0], getState());
        if (recipient == null) {
            player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
            return;
        }

        long quantity = Inputs.fromNumber(params[2]);
        if (quantity < 0L) {
            player.sendRawMessage(Messages.INVALID_NUMBER);
            return;
        }

        for (AssetStack as : actor.getAssets().get()) {
            if (as.getAsset().getName().equalsIgnoreCase(params[1])) {
                AssetStack stack = as.copy();
                stack.setQuantity(quantity);

                if (!actor.getAssets().contains(stack)) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ASSETS);
                    return;
                }

                Bukkit.getPluginManager().callEvent(new PaymentEvent(
                        actor,
                        recipient,
                        stack,
                        PaymentEvent.Cause.SEND_COMMAND
                ));

                return;
            }
        }

        player.sendRawMessage(Messages.ASSET_NOT_FOUND);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.ACTOR_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            list.addAll(Lists.ASSET_NAMES(getState()));
            if (!params[1].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
        } else if (params.length < 4) {
            list.add(Messages.INSERT_NUMBER);
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
