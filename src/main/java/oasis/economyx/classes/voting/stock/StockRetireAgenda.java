package oasis.economyx.classes.voting.stock;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.stock.StockRetiredEvent;
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
 * An agenda to retire stock
 */
public final class StockRetireAgenda implements Agenda, References {
    public StockRetireAgenda(@NonNull Shared shared, @NonNegative long shares) {
        this.shared = shared;
        this.shares = shares;
    }

    @Override
    @JsonIgnore
    public @NonNull String getDescription() {
        return "자사주 " + NumberFormat.getIntegerInstance().format(shares) + "주를 소각합니다.";
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private Shared shared;

    @NonNegative
    @JsonProperty
    private final long shares;

    @NonNull
    @JsonIgnore
    public Shared getShared() {
        return shared;
    }

    @NonNegative
    public long getShares() {
        return shares;
    }

    @Override
    @JsonIgnore
    public void run() {
        Bukkit.getPluginManager().callEvent(new StockRetiredEvent(
                shared,
                shares
        ));
    }

    @JsonProperty
    private final Type type = Type.STOCK_RETIRE;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"shared", "shares"})
    private StockRetireAgenda() {
        this.shared = null;
        this.shares = 0L;
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
