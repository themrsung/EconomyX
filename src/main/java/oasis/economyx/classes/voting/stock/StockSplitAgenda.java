package oasis.economyx.classes.voting.stock;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.stock.StockSplitEvent;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.interfaces.voting.Agenda;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;
import java.text.NumberFormat;

/**
 * An agenda to split stock
 */
public final class StockSplitAgenda implements Agenda, References {
    public StockSplitAgenda(@NonNull Shared shared, @NonNegative long newSharesPerShare) {
        this.shared = shared;
        this.newSharesPerShare = newSharesPerShare;
    }

    @Override
    @JsonIgnore
    public @NonNull String getDescription() {
        return "주식을 분할합니다. (기존 주식 1주당 " + NumberFormat.getIntegerInstance().format(newSharesPerShare) + " 주 지급)";
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private Shared shared;

    @NonNegative
    @JsonProperty
    private final long newSharesPerShare;

    @NonNull
    @JsonIgnore
    public Shared getShared() {
        return shared;
    }

    @NonNegative
    public long getNewSharesPerShare() {
        return newSharesPerShare;
    }

    @Override
    @JsonIgnore
    public void run() {
        Bukkit.getPluginManager().callEvent(new StockSplitEvent(
                shared,
                newSharesPerShare
        ));
    }

    @JsonProperty
    private final Type type = Type.STOCK_SPLIT;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"shared", "newSharesPerShare"})
    private StockSplitAgenda() {
        this.shared = null;
        this.newSharesPerShare = 0L;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        for (Shared orig : state.getShareds()) {
            if (orig.getUniqueId().equals(shared.getUniqueId())) {
                shared = orig;
                break;
            }
        }
    }
}
