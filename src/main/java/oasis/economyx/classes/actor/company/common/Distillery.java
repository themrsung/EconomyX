package oasis.economyx.classes.actor.company.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.ActorType;
import oasis.economyx.interfaces.actor.types.manufacturing.Distiller;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class Distillery extends Company implements Distiller {
    /**
     * Creates a new distillery
     * @param uniqueId Unique ID of this distillery
     * @param name Name of this distillery (not unique)
     * @param stockId ID of this distillery's stock
     * @param shareCount Initial share count
     * @param currency Currency to use
     */
    public Distillery(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);
    }

    public Distillery() {
        super();
    }

    public Distillery(Distillery other) {
        super(other);
    }

    @JsonProperty
    private final ActorType type = ActorType.DISTILLERY;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
