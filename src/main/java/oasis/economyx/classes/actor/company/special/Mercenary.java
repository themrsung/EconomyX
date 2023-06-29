package oasis.economyx.classes.actor.company.special;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.actor.types.services.Faction;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * aka PMC
 */
public final class Mercenary extends Company implements Faction {
    /**
     * Creates a new mercenary
     * @param uniqueId Unique ID of this mercenary
     * @param name Name of this mercenary (not unique)
     * @param stockId ID of this mercenary's stock
     * @param shareCount Initial share count
     * @param currency Currency to use
     */
    public Mercenary(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);
    }

    public Mercenary() {
        super();
    }

    public Mercenary(Mercenary other) {
        super(other);
    }

    @JsonProperty
    private final ActorType type = ActorType.MERCENARY;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
