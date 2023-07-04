package oasis.economyx.commands.sudo;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.commands.address.SetAddressCommand;
import oasis.economyx.commands.asset.DephysicalizeAssetCommand;
import oasis.economyx.commands.asset.PhysicalizeAssetCommand;
import oasis.economyx.commands.asset.SendAssetCommand;
import oasis.economyx.commands.balance.BalanceCommand;
import oasis.economyx.commands.create.CreateCommand;
import oasis.economyx.commands.info.InformationCommand;
import oasis.economyx.commands.message.MessageCommand;
import oasis.economyx.commands.message.ReplyCommand;
import oasis.economyx.commands.offer.OfferCommand;
import oasis.economyx.commands.pay.PayCommand;
import oasis.economyx.commands.retire.RetireCommand;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.List;

public final class SudoCommand extends EconomyCommand {
    public SudoCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
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

        final Actor executor = Inputs.searchActor(params[0], getState());
        if (executor == null) {
            player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
            return;
        }

        AccessLevel level = AccessLevel.getPermission(executor, actor);

        if (level == AccessLevel.OUTSIDER) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        final Keyword action = Keyword.fromInput(params[1]);
        final String[] argsToPass = params.length >= 3 ? Arrays.copyOfRange(params, 2, params.length) : new String[]{};

        if (action == null) {
            player.sendRawMessage(Messages.INVALID_KEYWORD);
            return;
        }

        switch (action) {
            case CREATE -> {
                CreateCommand create = new CreateCommand(getEX(), getState());
                create.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case BALANCE -> {
                BalanceCommand balance = new BalanceCommand(getEX(), getState());
                balance.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case SET_ADDRESS -> {
                SetAddressCommand setAddress = new SetAddressCommand(getEX(), getState());
                setAddress.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case PAY -> {
                PayCommand pay = new PayCommand(getEX(), getState());
                pay.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case MESSAGE -> {
                MessageCommand message = new MessageCommand(getEX(), getState());
                message.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case REPLY -> {
                ReplyCommand reply = new ReplyCommand(getEX(), getState());
                reply.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case PHYSICALIZE -> {
                PhysicalizeAssetCommand physicalize = new PhysicalizeAssetCommand(getEX(), getState());
                physicalize.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case DEPHYSICALIZE -> {
                DephysicalizeAssetCommand dephysicalize = new DephysicalizeAssetCommand(getEX(), getState());
                dephysicalize.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case INFO -> {
                InformationCommand info = new InformationCommand(getEX(), getState());
                info.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case SEND_ASSET -> {
                SendAssetCommand sendAsset = new SendAssetCommand(getEX(), getState());
                sendAsset.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case OFFER -> {
                OfferCommand offer = new OfferCommand(getEX(), getState());
                offer.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case RETIRE -> {
                RetireCommand retire = new RetireCommand(getEX(), getState());
                retire.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case SUDO -> {
                SudoCommand sudo = new SudoCommand(getEX(), getState());
                sudo.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            default -> {
                player.sendRawMessage(Messages.INVALID_KEYWORD);
            }
        }
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.ACTOR_NAMES(getState()));
        } else if (params.length < 3) {
            for (Keyword k : Keyword.values()) {
                list.addAll(k.toInput());
            }
        } else {
            final String[] argsToPass = Arrays.copyOfRange(params, 2, params.length);
            final Keyword action = Keyword.fromInput(params[1]);

            if (action == null) return;

            switch (action) {
                case CREATE -> {
                    CreateCommand create = new CreateCommand(getEX(), getState());
                    create.onEconomyComplete(list, argsToPass);
                }
                case BALANCE -> {
                    BalanceCommand balance = new BalanceCommand(getEX(), getState());
                    balance.onEconomyComplete(list, argsToPass);
                }
                case SET_ADDRESS -> {
                    SetAddressCommand setAddress = new SetAddressCommand(getEX(), getState());
                    setAddress.onEconomyComplete(list, argsToPass);
                }
                case PAY -> {
                    PayCommand pay = new PayCommand(getEX(), getState());
                    pay.onEconomyComplete(list, argsToPass);
                }
                case MESSAGE -> {
                    MessageCommand message = new MessageCommand(getEX(), getState());
                    message.onEconomyComplete(list, argsToPass);
                }
                case REPLY -> {
                    ReplyCommand reply = new ReplyCommand(getEX(), getState());
                    reply.onEconomyComplete(list, argsToPass);
                }
                case PHYSICALIZE -> {
                    PhysicalizeAssetCommand physicalize = new PhysicalizeAssetCommand(getEX(), getState());
                    physicalize.onEconomyComplete(list, argsToPass);
                }
                case DEPHYSICALIZE -> {
                    DephysicalizeAssetCommand dephysicalize = new DephysicalizeAssetCommand(getEX(), getState());
                    dephysicalize.onEconomyComplete(list, argsToPass);
                }
                case INFO -> {
                    InformationCommand info = new InformationCommand(getEX(), getState());
                    info.onEconomyComplete(list, argsToPass);
                }
                case SEND_ASSET -> {
                    SendAssetCommand sendAsset = new SendAssetCommand(getEX(), getState());
                    sendAsset.onEconomyComplete(list, argsToPass);
                }
                case OFFER -> {
                    OfferCommand offer = new OfferCommand(getEX(), getState());
                    offer.onEconomyComplete(list, argsToPass);
                }
                case RETIRE -> {
                    RetireCommand retire = new RetireCommand(getEX(), getState());
                    retire.onEconomyComplete(list, argsToPass);
                }
                case SUDO -> {
                    SudoCommand sudo = new SudoCommand(getEX(), getState());
                    sudo.onEconomyComplete(list, argsToPass);
                }
            }
        }
    }
}
