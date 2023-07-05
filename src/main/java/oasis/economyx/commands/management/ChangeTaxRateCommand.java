package oasis.economyx.commands.management;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.tax.TaxRateChangedEvent;
import oasis.economyx.events.tax.TaxType;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.Institutional;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;

public final class ChangeTaxRateCommand extends EconomyCommand {
    public ChangeTaxRateCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!(actor instanceof Sovereign s)) {
            player.sendRawMessage(Messages.ACTOR_NOT_SOVEREIGN);
            return;
        }

        boolean isRepresentative = false;

        for (Institutional i : s.getInstitutions()) {
            if (i.getType() == Actor.Type.LEGISLATURE) {
                if (Objects.equals(i.getRepresentative(), caller)) isRepresentative = true;
            }
        }

        if (!isRepresentative) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 2) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        TaxType type = TaxType.fromInput(params[0]);
        if (type == null) {
            player.sendRawMessage(Messages.INVALID_KEYWORD);
            return;
        }

        long input = Inputs.fromNumber(params[1]);
        if (input < 0L || input > 100L) {
            player.sendRawMessage(Messages.INVALID_NUMBER);
            return;
        }

        float rate = (float) input / 100;

        Bukkit.getPluginManager().callEvent(new TaxRateChangedEvent(s, rate, type));
        player.sendRawMessage(Messages.TAX_RATE_CHANGED);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            for (TaxType t : TaxType.values()) list.addAll(t.toInput());
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            list.add(Messages.INSERT_PERCENT);
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
