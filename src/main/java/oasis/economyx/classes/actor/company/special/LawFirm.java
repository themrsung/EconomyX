package oasis.economyx.classes.actor.company.special;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.actor.types.services.Legal;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class LawFirm extends Company implements Legal {
    /**
     * Create a new law firm
     * @param uniqueId Unique ID of this law firm
     * @param name Name of this law firm (not unique)
     * @param stockId ID of this law firm's stock
     * @param shareCount Initial share count
     * @param currency Currency to use
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
    private final ActorType type = ActorType.LAW_FIRM;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
