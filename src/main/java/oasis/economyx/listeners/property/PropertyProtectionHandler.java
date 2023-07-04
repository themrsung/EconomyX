package oasis.economyx.listeners.property;

import oasis.economyx.EconomyX;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.events.property.PropertyProtectedEvent;
import oasis.economyx.events.property.PropertyProtectorChangedEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.listeners.EconomyListener;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.address.Address;
import oasis.economyx.types.asset.AssetStack;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.property.PropertyMeta;
import oasis.economyx.types.asset.property.PropertyStack;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.block.Action;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.block.BlockPlaceEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntitySpawnEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public final class PropertyProtectionHandler extends EconomyListener {
    public PropertyProtectionHandler(@NonNull EconomyX EX, @NonNull EconomyState state) {
        super(EX, state);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPropertyProtectorChanged(PropertyProtectorChangedEvent e) {
        if (e.isCancelled()) return;

        PropertyProtector protector = e.getProtector();
        PropertyStack property = e.getProperty();

        PropertyMeta meta = property.getMeta();
        meta.setProtector(protector);
        property.setMeta(meta);
    }

    @EventHandler(priority = EventPriority.MONITOR)
    public void onPropertyProtected(PropertyProtectedEvent e) {
        if (e.isCancelled()) return;

        Actor owner = e.getOwner();
        PropertyStack property = e.getProperty();
        PropertyProtector protector = property.getMeta().getProtector();

        // Whether protector is null should be checked before throwing this event.
        if (protector == null) throw new RuntimeException();

        CashStack fee = protector.getProtectionFee();

        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                owner,
                protector,
                fee,
                PaymentEvent.Cause.PROPERTY_PROTECTION_FEE
        ));
    }

    //
    // Property Protection
    //

    /**
     * Process property protection.
     *
     * @param player   Player
     * @param location Location
     * @return Whether player has access to location
     */
    private boolean onPropertyAccessAttempted(Player player, Location location) {
        Actor owner = null;
        PropertyStack property = null;

        for (Actor a : getState().getActors()) {
            for (AssetStack as : a.getAssets().get()) {
                if (as instanceof PropertyStack ps) {
                    if (ps.getAsset().getArea().contains(Address.fromLocation(location))) {
                        owner = a;
                        property = ps;
                    }
                }
            }
        }

        if (owner == null) return false;

        // Check if protector is assigned
        PropertyProtector protector = property.getMeta().getProtector();
        if (protector == null) return false;

        // Check solvency of owner
        CashStack fee = protector.getProtectionFee();
        if (!owner.getPayableAssets(getState()).contains(fee)) return false;

        boolean hasAccess = false;

        // Get person trying to access property
        try {
            Person person = getState().getPerson(player.getUniqueId());

            // TODO Add construction contracts
            // TODO Check if person is employee of contractor

            if (owner instanceof Person p) {
                if (p.equals(person)) hasAccess = true;
            }

            if (owner instanceof Representable r) {
                if (Objects.equals(r.getRepresentative(), person)) hasAccess = true;
            }

            if (owner instanceof Employer e) {
                hasAccess = hasAccess || e.getEmployees().contains(person) || e.getDirectors().contains(person);
            }

            if (!hasAccess) {
                // Charge protection fee
                Bukkit.getPluginManager().callEvent(new PropertyProtectedEvent(
                        property,
                        owner
                ));
            }

            return !hasAccess;
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockPlaced(BlockPlaceEvent e) {
        if (e.isCancelled()) return;
        e.setCancelled(onPropertyAccessAttempted(e.getPlayer(), e.getBlockPlaced().getLocation()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onBlockBroken(BlockBreakEvent e) {
        if (e.isCancelled()) return;
        e.setCancelled(onPropertyAccessAttempted(e.getPlayer(), e.getBlock().getLocation()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onPlayerInteract(PlayerInteractEvent e) {
        Action a = e.getAction();

        // TODO Figure out what the new methods do (isCancelled() is deprecated)
        if (a == Action.RIGHT_CLICK_AIR || a == Action.LEFT_CLICK_AIR || e.isCancelled()) return;

        assert e.getClickedBlock() != null;
        e.setCancelled(onPropertyAccessAttempted(e.getPlayer(), e.getClickedBlock().getLocation()));
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntityDamaged(EntityDamageByEntityEvent e) {
        if (e.isCancelled()) return;

        if (e.getDamager() instanceof Player p) {
            if (!PROTECTED_ENTITIES.contains(e.getEntityType())) return;

            e.setCancelled(onPropertyAccessAttempted(p, e.getEntity().getLocation()));
        }
    }

    @EventHandler(priority = EventPriority.LOWEST)
    public void onEntitySpawned(EntitySpawnEvent e) {
        if (e.isCancelled()) return;

        for (AssetStack as : getState().getAssets()) {
            if (as instanceof PropertyStack ps) {
                if (ps.getAsset().getArea().contains(Address.fromLocation(e.getLocation()))) {
                    if (PROHIBITED_ENTITIES.contains(e.getEntityType())) {
                        e.setCancelled(true);
                        return;
                    }
                }
            }
        }
    }

    /**
     * These entities are protected from PvE damage within claimed areas.
     */
    private static final List<EntityType> PROTECTED_ENTITIES = Arrays.asList(
            EntityType.LEASH_HITCH,
            EntityType.PAINTING,
            EntityType.ITEM_FRAME,
            EntityType.ITEM_DISPLAY,
            EntityType.ARMOR_STAND,
            EntityType.PIG,
            EntityType.CHICKEN,
            EntityType.WOLF,
            EntityType.OCELOT,
            EntityType.IRON_GOLEM,
            EntityType.HORSE,
            EntityType.POLAR_BEAR,
            EntityType.VILLAGER,
            EntityType.CAT,
            EntityType.GLOW_ITEM_FRAME,
            EntityType.TEXT_DISPLAY
    );

    /**
     * Prohibited entities will not be allowed to spawn within protected areas.
     */
    private static final List<EntityType> PROHIBITED_ENTITIES = Arrays.asList(
            EntityType.PHANTOM,
            EntityType.ZOMBIE,
            EntityType.SKELETON,
            EntityType.ENDERMAN,
            EntityType.CREEPER
    );
}
