package oasis.economyx.classes.voting.stock;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.stock.StockRetiredEvent;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.interfaces.voting.Agenda;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.bukkit.Bukkit;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;
import java.text.NumberFormat;

/**
 * An agenda to retire stock
 */
public final class StockRetireAgenda implements Agenda {
    public StockRetireAgenda(@NonNull Shared shared, @NonNegative long shares) {
        this.shared = shared;
        this.shares = shares;
    }

    @Override
    @JsonIgnore
    public @NonNull String getDescription() {
        return "Retire " + NumberFormat.getIntegerInstance().format(shares) + " shares";
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Shared shared;

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
}
