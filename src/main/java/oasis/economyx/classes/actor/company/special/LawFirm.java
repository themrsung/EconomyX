package oasis.economyx.classes.actor.company.special;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.services.Legal;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class LawFirm extends Company implements Legal {
    /**
     * Create a new law firm
     *
     * @param uniqueId   Unique ID of this law firm
     * @param name       Name of this law firm (not unique)
     * @param stockId    ID of this law firm's stock
     * @param shareCount Initial share count
     * @param currency   Currency to use
     */
    public LawFirm(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);
    }

    public LawFirm() {
        super();
    }

    public LawFirm(LawFirm other) {
        super(other);
    }

    @JsonProperty
    private final Type type = Type.LAW_FIRM;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
