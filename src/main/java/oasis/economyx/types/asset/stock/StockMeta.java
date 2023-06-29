package oasis.economyx.types.asset.stock;

import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.AssetMeta;
import oasis.economyx.types.asset.AssetType;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

public final class StockMeta implements AssetMeta {
    public StockMeta() {
        this.voter = null;
    }

    public StockMeta(StockMeta other) {
        this.voter = other.voter;
    }

    /**
     * The person who has voting rights to this share.
     * Will be null if owner is voter.
     * This is ignored for self-owned shares. (there are no voting rights for self owned shares)
     */
    @Nullable
    private Actor voter;

    @Nullable
    public Actor getVoter() {
        return voter;
    }

    public void setVoter(@Nullable Actor voter) {
        this.voter = voter;
    }

    @NonNull
    @JsonProperty
    private final AssetType type = AssetType.STOCK;

    @NotNull
    @Override
    @JsonProperty
    public AssetType getType() {
        return type;
    }
}
