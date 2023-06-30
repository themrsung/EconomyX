package oasis.economyx.classes.voting.stock;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.interfaces.voting.Agenda;
import oasis.economyx.types.asset.stock.Stock;
import oasis.economyx.types.asset.stock.StockStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;
import java.text.NumberFormat;

/**
 * An agenda to split stock
 */
public final class StockSplitAgenda implements Agenda {
    public StockSplitAgenda(@NonNull Shared shared, @NonNegative long newSharesPerShare) {
        this.shared = shared;
        this.newSharesPerShare = newSharesPerShare;
    }

    @Override
    @JsonIgnore
    public @NonNull String getDescription() {
        return "Retire " + NumberFormat.getIntegerInstance().format(newSharesPerShare) + " shares";
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Shared shared;

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
        try {
            shared.getAssets().remove(new StockStack(
                    new Stock(shared.getStockId()),
                    newSharesPerShare
            ));
        } catch (IllegalArgumentException e) {
            // Insufficient self-owned shares
        }

        shared.reduceShareCount(newSharesPerShare);
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
}
