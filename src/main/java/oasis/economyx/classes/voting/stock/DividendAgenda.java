package oasis.economyx.classes.voting.stock;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.dividend.DividendEvent;
import oasis.economyx.interfaces.actor.types.ownership.Shared;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.interfaces.voting.Agenda;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * An agenda to pay dividends to shareholders
 */
public final class DividendAgenda implements Agenda, References {
    public DividendAgenda(@NonNull Shared shared, @NonNull CashStack dividendPerShare) {
        this.shared = shared;
        this.dividendPerShare = dividendPerShare;
    }

    @Override
    @JsonIgnore
    public @NonNull String getDescription() {
        return "Pay dividends";
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private Shared shared;

    @NonNull
    @JsonProperty
    private final CashStack dividendPerShare;

    @NonNull
    @JsonIgnore
    public Shared getShared() {
        return shared;
    }

    @NonNull
    public CashStack getDividendPerShare() {
        return dividendPerShare;
    }

    @Override
    @JsonIgnore
    public void run() {
        Bukkit.getPluginManager().callEvent(new DividendEvent(
                shared,
                dividendPerShare
        ));
    }

    @JsonProperty
    private final Type type = Type.DIVIDEND;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }

    @ConstructorProperties({"shared", "dividendPerShare"})
    private DividendAgenda() {
        this.shared = null;
        this.dividendPerShare = null;
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
