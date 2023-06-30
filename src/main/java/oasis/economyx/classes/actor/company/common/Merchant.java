package oasis.economyx.classes.actor.company.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class Merchant extends Company implements CardAcceptor {
    /**
     * Creates a new merchant
     *
     * @param uniqueId   Unique ID of this merchant
     * @param name       Name of this merchant (not unique)
     * @param stockId    ID of this merchant's stock
     * @param shareCount Initial share count
     * @param currency   Currency to use
     */
    public Merchant(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);
    }

    public Merchant() {
        super();
    }

    public Merchant(Merchant other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.MERCHANT;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
