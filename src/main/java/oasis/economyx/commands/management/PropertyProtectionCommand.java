package oasis.economyx.commands.management;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.management.PropertyProtectionFeeChangedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.address.Address;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.property.PropertyStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.Arrays;
import java.util.List;

public final class PropertyProtectionCommand extends EconomyCommand {
    public PropertyProtectionCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DIRECTOR)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
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
            case SET_PROTECTION_FEE -> {
                if (!(actor instanceof PropertyProtector protector)) {
                    player.sendRawMessage(Messages.ACTOR_NOT_PROPERTY_PROTECTOR);
                    return;
                }

                if (params.length < 3) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                    return;
                }

                Cash currency = Inputs.searchCurrency(params[1], getState());
                if (currency == null) {
                    player.sendRawMessage(Messages.INVALID_CURRENCY);
                    return;
                }

                long amount = Inputs.fromNumber(params[2]);
                if (amount < 0L) {
                    player.sendRawMessage(Messages.INVALID_NUMBER);
                    return;
                }

                CashStack fee = new CashStack(currency, amount);
                Bukkit.getPluginManager().callEvent(new PropertyProtectionFeeChangedEvent(protector, fee));
                player.sendRawMessage(Messages.PROPERTY_PROTECTION_FEE_CHANGED);
            }
            case WHO_IS_OWNER -> {
                Address query = Address.fromLocation(player.getLocation());

                for (Actor a : getState().getActors()) {
                    for (AssetStack as : a.getAssets().get()) {
                        if (as instanceof PropertyStack ps) {
                            if (ps.getAsset().getArea().contains(query)) {
                                player.sendRawMessage(Messages.OWNER_OF_ADDRESS(a, query));
                                return;
                            }
                        }
                    }
                }

                for (AssetStack as : getState().getAssets()) {
                    if (as instanceof PropertyStack ps) {
                        if (ps.getAsset().getArea().contains(query)) {
                            player.sendRawMessage(Messages.OWNER_OF_ADDRESS(null, query));
                            return;
                        }
                    }
                }

                player.sendRawMessage(Messages.OWNER_NOT_FOUND);
            }
            default -> {
                player.sendRawMessage(Messages.INVALID_KEYWORD);
            }
        }
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            for (Action a : Action.values()) list.addAll(a.toInput());
            if (!params[0].equals("")) list.removeIf(s -> !s.startsWith(params[0].toLowerCase()));
        } else {
            Action action = Action.fromInput(params[0]);
            if (action != null) {
                switch (action) {
                    case SET_PROTECTION_FEE -> {
                        if (params.length < 3) {
                            list.addAll(Lists.CURRENCY_NAMES(getState()));
                            if (!params[1].equals("")) list.removeIf(s -> !s.startsWith(params[1].toLowerCase()));
                        } else if (params.length < 4) {
                            list.add(Messages.INSERT_NUMBER);
                        } else {
                            list.add(Messages.ALL_DONE);
                        }
                    }
                    case WHO_IS_OWNER -> {
                        list.add(Messages.ALL_DONE);
                    }
                }
            }
        }
    }

    private enum Action {
        SET_PROTECTION_FEE,
        WHO_IS_OWNER;

        private static final List<String> K_SET_PROTECTION_FEE = Arrays.asList("fee", "protectionfee", "setprotectionfee", "보호비", "보호비설정");
        private static final List<String> K_WHO_IS_OWNER = Arrays.asList("here", "whois", "owner", "여기", "주인", "지주");

        @Nullable
        public static Action fromInput(@NonNull String input) {
            if (K_SET_PROTECTION_FEE.contains(input.toLowerCase())) return SET_PROTECTION_FEE;
            if (K_WHO_IS_OWNER.contains(input.toLowerCase())) return WHO_IS_OWNER;
            return null;
        }

        @NonNull
        public List<String> toInput() {
            return switch (this) {
                case SET_PROTECTION_FEE -> K_SET_PROTECTION_FEE;
                case WHO_IS_OWNER -> K_WHO_IS_OWNER;
            };
        }
    }
}
