package oasis.economyx.interfaces.actor.types.warfare;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.List;

/**
 * A faction represents a party during wartime
 */
public interface Faction extends Actor {
    /**
     * Gets all factions this faction is hostile to.
     *
     * @return Hostile factions
     */
    @NonNull
    @JsonIgnore
    List<Faction> getHostilities();

    /**
     * Declares hostility against given faction.
     *
     * @param faction Faction to declare hostility on
     */
    @JsonIgnore
    void addHostility(@NonNull Faction faction);

    /**
     * Revokes hostility against given faction.
     *
     * @param faction Faction to remove hostility from
     */
    @JsonIgnore
    void removeHostility(@NonNull Faction faction);

    /**
     * Gets all enemies. (Outbound and inbound hostilities.
     *
     * @return Enemies
     */
    @JsonIgnore
    default List<Faction> getEnemies(@NonNull EconomyState state) {
        List<Faction> enemies = new ArrayList<>();

        for (Faction f : state.getFactions()) {
            if (this.getHostilities().contains(f) || f.getHostilities().contains(this)) {
                if (!enemies.contains(f)) enemies.add(f);
            }
        }

        return enemies;
    }
}
