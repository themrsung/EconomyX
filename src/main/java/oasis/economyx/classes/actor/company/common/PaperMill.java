package oasis.economyx.classes.actor.company.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.ActorType;
import oasis.economyx.actor.types.manufacturing.BillCreator;
import oasis.economyx.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

public final class PaperMill extends Company implements BillCreator {
    /**
     * Creates a new paper mill
     * @param uniqueId Unique ID of this paper mill
     * @param name Name of this paper mill (not unique)
     * @param stockId ID of this paper mill's stock
     * @param shareCount Initial share count
     * @param currency Currency to use
     */
    public PaperMill(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);
    }

    public PaperMill() {
        super();
    }

    public PaperMill(PaperMill other) {
        super(other);
    }

    @JsonProperty
    private final ActorType type = ActorType.PAPER_MILL;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
