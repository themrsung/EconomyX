package oasis.economyx.commands.offer;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.offer.OfferAcceptedEvent;
import oasis.economyx.events.offer.OfferDeclinedEvent;
import oasis.economyx.events.offer.OfferSentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.offer.Offer;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public final class OfferCommand extends EconomyCommand {
    public OfferCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
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

        Keyword word = Keyword.fromInput(params[0]);
        if (word == null) {
            player.sendRawMessage(Messages.INVALID_KEYWORD);
            return;
        }

        switch (word) {
            case ACCEPT, DENY -> {
                if (params.length < 2) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                    return;
                }

                Actor sender = Inputs.searchActor(params[1], getState());
                if (sender == null) {
                    player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
                    return;
                }

                List<Offer> offers = getState().getOffersByRecipient(actor);
                if (offers.size() == 0) {
                    player.sendRawMessage(Messages.NO_OFFERS_RECEIVED);
                    return;
                }

                final Offer offer = offers.get(0);
                switch (word) {
                    case ACCEPT -> {
                        Bukkit.getPluginManager().callEvent(new OfferAcceptedEvent(offer));
                        return;
                    }
                    case DENY -> {
                        Bukkit.getPluginManager().callEvent(new OfferDeclinedEvent(offer));
                        return;
                    }
                    default -> {
                        player.sendRawMessage(Messages.UNKNOWN_ERROR);
                        return;
                    }
                }
            }
            case CREATE -> {
                if (params.length < 3) {
                    player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
                    return;
                }

                Offer.Type type = Offer.Type.fromInput(params[1]);
                if (type == null) {
                    player.sendRawMessage(Messages.INVALID_KEYWORD);
                    return;
                }

                if (type == Offer.Type.REPRESENTATIVE_OFFER) {
                    player.sendRawMessage(Messages.ILLEGAL_OFFER_SIGNATURE);
                    return;
                }

                Actor recipient = Inputs.searchActor(params[2], getState());
                if (recipient == null) {
                    player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
                    return;
                }

                for (Offer o : getState().getOffers()) {
                    if (o.getSender().equals(actor) && o.getRecipient().equals(recipient)) {
                        player.sendRawMessage(Messages.OFFER_ALREADY_SENT);
                        return;
                    }
                }

                try {
                    Offer offer = Offer.getNew(actor, recipient, type);
                    Bukkit.getPluginManager().callEvent(new OfferSentEvent(offer));
                    return;
                } catch (IllegalArgumentException e) {
                    player.sendRawMessage(Messages.ILLEGAL_OFFER_SIGNATURE);
                    return;
                }
            }
        }

        player.sendRawMessage(Messages.INVALID_KEYWORD);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Offer.Type.EMPLOYEE_OFFER.toInput());
            list.addAll(Offer.Type.DIRECTOR_OFFER.toInput());
            list.addAll(Offer.Type.MEMBERSHIP_OFFER.toInput());
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else if (params.length < 3) {
            Keyword k = Keyword.fromInput(params[0]);
            if (k != null) {
                switch (k) {
                    case ACCEPT, DENY -> {
                        list.addAll(Lists.ACTOR_NAMES(getState()));
                        if (!params[1].equals(""))
                            list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
                    }
                    case CREATE -> {
                        list.addAll(Offer.Type.EMPLOYEE_OFFER.toInput());
                        list.addAll(Offer.Type.DIRECTOR_OFFER.toInput());
                        list.addAll(Offer.Type.MEMBERSHIP_OFFER.toInput());
                        if (!params[1].equals(""))
                            list.removeIf(s -> !s.toLowerCase().startsWith(params[1].toLowerCase()));
                    }
                }
            }
        } else if (params.length < 4) {
            Keyword k = Keyword.fromInput(params[0]);
            if (k != null) {
                switch (k) {
                    case ACCEPT, DENY -> {
                        list.add(Messages.ALL_DONE);
                    }
                    case CREATE -> {
                        list.addAll(Lists.ACTOR_NAMES(getState()));
                        if (!params[2].equals(""))
                            list.removeIf(s -> !s.toLowerCase().startsWith(params[2].toLowerCase()));
                    }
                }
            }
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
