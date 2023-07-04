package oasis.economyx.commands.retire;

import oasis.economyx.EconomyX;
import oasis.economyx.classes.actor.organization.corporate.Cartel;
import oasis.economyx.classes.actor.organization.international.Alliance;
import oasis.economyx.classes.actor.organization.personal.Party;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.organization.alliance.AllianceMemberRemovedEvent;
import oasis.economyx.events.organization.cartel.CartelMemberRemovedEvent;
import oasis.economyx.events.organization.party.PartyMemberRemovedEvent;
import oasis.economyx.events.personal.employment.DirectorFiredEvent;
import oasis.economyx.events.personal.employment.EmployeeFiredEvent;
import oasis.economyx.events.personal.representable.RepresentativeFiredEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.Objects;

public final class RetireCommand extends EconomyCommand {
    public RetireCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.DE_FACTO_SELF)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 1) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        Actor organization = Inputs.searchActor(params[0], getState());
        if (organization == null) {
            player.sendRawMessage(Messages.ACTOR_NOT_FOUND);
            return;
        }

        if (organization instanceof Employer e && actor instanceof Person p) {
            if (e.getEmployees().contains(p)) {
                Bukkit.getPluginManager().callEvent(new EmployeeFiredEvent(p, e));
                player.sendRawMessage(Messages.RETIRED_FROM_EMPLOYER);
                return;
            }

            if (e.getDirectors().contains(p)) {
                Bukkit.getPluginManager().callEvent(new DirectorFiredEvent(p, e));
                player.sendRawMessage(Messages.RETIRED_FROM_EMPLOYER);
                return;
            }
        }

        // Since organizations are representables, actor must retire from representative first
        if (organization instanceof Representable r && actor instanceof Person p) {
            if (Objects.equals(r.getRepresentative(), p)) {
                Bukkit.getPluginManager().callEvent(new RepresentativeFiredEvent(r));
                player.sendRawMessage(Messages.RETIRED_FROM_REPRESENTABLE);
                return;
            }
        }

        if (organization instanceof Alliance a && actor instanceof Sovereign s) {
            if (a.getMembers().contains(s)) {
                Bukkit.getPluginManager().callEvent(new AllianceMemberRemovedEvent(a, s));
                player.sendRawMessage(Messages.RETIRED_FROM_ORGANIZATION);
                return;
            }
        }

        if (organization instanceof Cartel car && actor instanceof Corporation corp) {
            if (car.getMembers().contains(corp)) {
                Bukkit.getPluginManager().callEvent(new CartelMemberRemovedEvent(car, corp));
                player.sendRawMessage(Messages.RETIRED_FROM_ORGANIZATION);
                return;
            }
        }

        if (organization instanceof Party par && actor instanceof Person per) {
            if (par.getMembers().contains(per)) {
                Bukkit.getPluginManager().callEvent(new PartyMemberRemovedEvent(par, per));
                player.sendRawMessage(Messages.RETIRED_FROM_ORGANIZATION);
                return;
            }
        }

        player.sendRawMessage(Messages.ILLEGAL_RETIRE_SIGNATURE);
    }

    @Override
    public void onEconomyComplete(@NonNull List<String> list, @NonNull String[] params) {
        if (params.length < 2) {
            list.addAll(Lists.ACTOR_NAMES(getState()));
            if (!params[0].equals("")) list.removeIf(s -> !s.toLowerCase().startsWith(params[0].toLowerCase()));
        } else {
            list.add(Messages.ALL_DONE);
        }
    }
}
