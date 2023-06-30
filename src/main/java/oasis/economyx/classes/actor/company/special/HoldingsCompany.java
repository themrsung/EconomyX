package oasis.economyx.classes.actor.company.special;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * Holdings companies do not have any special abilities
 * They are empty vessels intended to be put on top of a corporate representable hierarchy
 */
public final class HoldingsCompany extends Company {
    /**
     * Creates a new holdings company
     * @param uniqueId Unique ID of this company
     * @param name Name of this company (not unique)
     * @param stockId ID of this company's stock
     * @param shareCount Initial share count
     * @param currency Currency to use
     */
    public HoldingsCompany(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);
    }

    public HoldingsCompany() {
        super();
    }

    public HoldingsCompany(HoldingsCompany other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.HOLDINGS_COMPANY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
