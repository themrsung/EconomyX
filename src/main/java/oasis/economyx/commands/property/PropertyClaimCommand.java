package oasis.economyx.commands.property;

import oasis.economyx.EconomyX;
import oasis.economyx.commands.EconomyCommand;
import oasis.economyx.events.property.PropertyClaimedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.address.Address;
import oasis.economyx.types.address.Area;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.property.Property;
import oasis.economyx.types.asset.property.PropertyStack;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;
import java.util.UUID;

public final class PropertyClaimCommand extends EconomyCommand {
    public static final long MAX_CLAIM_AREA = 201 * 201;

    public PropertyClaimCommand(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @Override
    public void onEconomyCommand(@NonNull Player player, @NonNull Person caller, @NonNull Actor actor, @NonNull String[] params, @NonNull AccessLevel permission) {
        if (!permission.isAtLeast(AccessLevel.EMPLOYEE)) {
            player.sendRawMessage(Messages.INSUFFICIENT_PERMISSIONS);
            return;
        }

        if (params.length < 1) {
            player.sendRawMessage(Messages.INSUFFICIENT_ARGS);
            return;
        }

        long radius = Inputs.fromNumber(params[0]);
        if (radius < 0L) {
            player.sendRawMessage(Messages.INVALID_NUMBER);
            return;
        }

        Address center = Address.fromLocation(player.getLocation());
        Address pointA = new Address(center.world(), center.x() - radius, center.y(), center.z() - radius, 0f, 0f);
        Address pointB = new Address(center.world(), center.x() + radius, center.y(), center.z() + radius, 0f, 0f);

        Area area = new Area(pointA, pointB);

        if (area.area() > MAX_CLAIM_AREA) {
            player.sendRawMessage(Messages.PROPERTY_CLAIM_OVER_LIMIT);
            return;
        }

        for (AssetStack as : getState().getAssets()) {
            if (as instanceof PropertyStack ps) {
                try {
                    if (ps.getAsset().getArea().contains(area)) {
                        player.sendRawMessage(Messages.PROPERTY_OVERLAPS_ANOTHER);
                        return;
                    }
                } catch (RuntimeException e) {
                    // Move on
                }
            }
        }

        Property property = new Property(UUID.randomUUID(), area);
        PropertyStack stack = new PropertyStack(property);

        Bukkit.getPluginManager().callEvent(new PropertyClaimedEvent(stack, actor));
        player.sendRawMessage(Messages.PROPERTY_CLAIMED);
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
