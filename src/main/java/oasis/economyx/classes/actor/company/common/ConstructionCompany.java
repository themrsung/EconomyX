package oasis.economyx.classes.actor.company.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.services.Builder;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class ConstructionCompany extends Company implements Builder {
    /**
     * Creates a new construction company
     * @param uniqueId Unique ID of this company
     * @param name Name of this company (not unique)
     * @param stockId ID of this company's stock
     * @param shareCount Initial share count of this company
     * @param currency The currency this company should use
     */
    public ConstructionCompany(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);
    }

    public ConstructionCompany() {
        super();
    }

    public ConstructionCompany(ConstructionCompany other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.CONSTRUCTION_COMPANY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
