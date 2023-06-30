package oasis.economyx.classes.actor.company.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.manufacturing.Producer;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class Manufacturer extends Company implements Producer {
    /**
     * Creates a new manufacturing
     * @param uniqueId Unique ID of this manufacturing
     * @param name Name of this manufacturing (not unique)
     * @param stockId ID of this manufacturing's stock
     * @param shareCount Initial share count
     * @param currency Currency to use
     */
    public Manufacturer(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);
    }

    public Manufacturer() {
        super();
    }

    public Manufacturer(Manufacturer other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.MANUFACTURER;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
