package oasis.economyx.commands.sudo;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.commands.address.AddressCommand;
import oasis.economyx.commands.address.SetAddressCommand;
import oasis.economyx.commands.asset.DephysicalizeAssetCommand;
import oasis.economyx.commands.asset.PhysicalizeAssetCommand;
import oasis.economyx.commands.asset.SendAssetCommand;
import oasis.economyx.commands.balance.BalanceCommand;
import oasis.economyx.commands.create.CreateCommand;
import oasis.economyx.commands.info.InformationCommand;
import oasis.economyx.commands.join.JoinCommand;
import oasis.economyx.commands.management.*;
import oasis.economyx.commands.message.MessageCommand;
import oasis.economyx.commands.message.ReplyCommand;
import oasis.economyx.commands.offer.OfferCommand;
import oasis.economyx.commands.pay.PayCommand;
import oasis.economyx.commands.property.PropertyAbandonCommand;
import oasis.economyx.commands.property.PropertyClaimCommand;
import oasis.economyx.commands.property.PropertySetProtectorCommand;
import oasis.economyx.commands.retire.RetireCommand;
import oasis.economyx.commands.trading.order.OrderCommand;
import oasis.economyx.commands.voting.VoteCommand;
import oasis.economyx.commands.warfare.HostilityCommand;
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
            case ADDRESS -> {
                AddressCommand address = new AddressCommand(getEX(), getState());
                address.onEconomyCommand(player, caller, executor, argsToPass, level);
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
            case CLAIM_PROPERTY -> {
                PropertyClaimCommand claim = new PropertyClaimCommand(getEX(), getState());
                claim.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case ABANDON_PROPERTY -> {
                PropertyAbandonCommand abandon = new PropertyAbandonCommand(getEX(), getState());
                abandon.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case SET_PROPERTY_PROTECTOR -> {
                PropertySetProtectorCommand setProtector = new PropertySetProtectorCommand(getEX(), getState());
                setProtector.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case VOTE -> {
                VoteCommand vote = new VoteCommand(getEX(), getState());
                vote.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case PROPERTY_PROTECTION -> {
                PropertyProtectionCommand propertyProtection = new PropertyProtectionCommand(getEX(), getState());
                propertyProtection.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case MANAGE_INSTITUTION -> {
                ManageInstitutionCommand manageInstitution = new ManageInstitutionCommand(getEX(), getState());
                manageInstitution.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case ISSUE_CURRENCY -> {
                IssueCurrencyCommand issueCurrency = new IssueCurrencyCommand(getEX(), getState());
                issueCurrency.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case CHANGE_TAX_RATE -> {
                ChangeTaxRateCommand changeTaxRate = new ChangeTaxRateCommand(getEX(), getState());
                changeTaxRate.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case JOIN -> {
                JoinCommand join = new JoinCommand(getEX(), getState());
                join.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case HOSTILITY -> {
                HostilityCommand hostility = new HostilityCommand(getEX(), getState());
                hostility.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case ASSET_LISTING -> {
                AssetListingCommand assetListing = new AssetListingCommand(getEX(), getState());
                assetListing.onEconomyCommand(player, caller, executor, argsToPass, level);
            }
            case ORDER -> {
                OrderCommand order = new OrderCommand(getEX(), getState());
                order.onEconomyCommand(player, caller, executor, argsToPass, level);
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
            if (!(params[0].equals(""))) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            for (Keyword k : Keyword.values()) {
                list.addAll(k.toInput());
            }
            if (!(params[1].equals(""))) list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
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
                case ADDRESS -> {
                    AddressCommand address = new AddressCommand(getEX(), getState());
                    address.onEconomyComplete(list, argsToPass);
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
                case CLAIM_PROPERTY -> {
                    PropertyClaimCommand claim = new PropertyClaimCommand(getEX(), getState());
                    claim.onEconomyComplete(list, argsToPass);
                }
                case ABANDON_PROPERTY -> {
                    PropertyAbandonCommand abandon = new PropertyAbandonCommand(getEX(), getState());
                    abandon.onEconomyComplete(list, argsToPass);
                }
                case SET_PROPERTY_PROTECTOR -> {
                    PropertySetProtectorCommand setProtector = new PropertySetProtectorCommand(getEX(), getState());
                    setProtector.onEconomyComplete(list, argsToPass);
                }
                case VOTE -> {
                    VoteCommand vote = new VoteCommand(getEX(), getState());
                    vote.onEconomyComplete(list, argsToPass);
                }
                case PROPERTY_PROTECTION -> {
                    PropertyProtectionCommand propertyProtection = new PropertyProtectionCommand(getEX(), getState());
                    propertyProtection.onEconomyComplete(list, argsToPass);
                }
                case MANAGE_INSTITUTION -> {
                    ManageInstitutionCommand manageInstitution = new ManageInstitutionCommand(getEX(), getState());
                    manageInstitution.onEconomyComplete(list, argsToPass);
                }
                case ISSUE_CURRENCY -> {
                    IssueCurrencyCommand issueCurrency = new IssueCurrencyCommand(getEX(), getState());
                    issueCurrency.onEconomyComplete(list, argsToPass);
                }
                case CHANGE_TAX_RATE -> {
                    ChangeTaxRateCommand changeTaxRate = new ChangeTaxRateCommand(getEX(), getState());
                    changeTaxRate.onEconomyComplete(list, argsToPass);
                }
                case JOIN -> {
                    JoinCommand join = new JoinCommand(getEX(), getState());
                    join.onEconomyComplete(list, argsToPass);
                }
                case HOSTILITY -> {
                    HostilityCommand hostility = new HostilityCommand(getEX(), getState());
                    hostility.onEconomyComplete(list, argsToPass);
                }
                case ASSET_LISTING -> {
                    AssetListingCommand assetListing = new AssetListingCommand(getEX(), getState());
                    assetListing.onEconomyComplete(list, argsToPass);
                }
                case ORDER -> {
                    OrderCommand order = new OrderCommand(getEX(), getState());
                    order.onEconomyComplete(list, argsToPass);
                }
                case SUDO -> {
                    SudoCommand sudo = new SudoCommand(getEX(), getState());
                    sudo.onEconomyComplete(list, argsToPass);
                }
            }
        }
    }
}
