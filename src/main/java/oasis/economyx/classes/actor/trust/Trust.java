package oasis.economyx.classes.actor.trust;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.EconomicActor;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.fund.Fund;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import oasis.economyx.types.asset.stock.Stock;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * The instantiable class of Fund
 */
public final class Trust extends EconomicActor implements Fund {
    /**
     * Constructs a new trust
     * @param uniqueId Unique ID of this trust
     * @param name Name of this trust (not unique)
     * @param stockId Unique ID of this trust's stock
     * @param shareCount Initial share count
     * @param currency Currency to pay the trustee in
     */
    public Trust(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name);
        this.stockId = stockId;
        this.shareCount = shareCount;
        this.trusteePay = new CashStack(currency, 0L);
    }

    public Trust() {
        super();
        this.stockId = UUID.randomUUID();
        this.shareCount = 0L;
        this.trustee = null;
        this.trusteePay = null;
    }

    public Trust(Trust other) {
        super(other);
        this.stockId = other.stockId;
        this.shareCount = other.shareCount;
        this.trustee = other.trustee;
        this.trusteePay = other.trusteePay;
    }

    @JsonProperty
    private final UUID stockId;
    @JsonProperty
    private long shareCount;

    @Nullable
    @JsonProperty
    @JsonIdentityReference
    private Person trustee;

    @NonNull
    @JsonProperty
    private CashStack trusteePay;

    @Override
    @JsonIgnore
    public @Nullable Person getRepresentative() {
        return trustee;
    }

    @Override
    @JsonIgnore
    public void setRepresentative(Person representative) {
        this.trustee = representative;
    }

    @Override
    public @NonNull CashStack getRepresentativePay() {
        return new CashStack(trusteePay);
    }

    @Override
    public void setRepresentativePay(@NonNull CashStack pay) {
        this.trusteePay = pay;
    }

    @Override
    @JsonIgnore
    public @NonNull UUID getStockId() {
        return stockId;
    }

    @Override
    @JsonIgnore
    public @NonNegative long getShareCount() {
        return shareCount;
    }

    @Override
    @JsonIgnore
    public void setShareCount(@NonNegative long shares) {
        this.shareCount = shares;
    }

    @Override
    @JsonIgnore
    public void addShareCount(@NonNegative long delta) {
        this.shareCount += delta;
    }

    @Override
    @JsonIgnore
    public void reduceShareCount(@NonNegative long delta) throws IllegalArgumentException {
        if (shareCount - delta < 1) throw new IllegalArgumentException();
        this.shareCount -= delta;
    }

    @Override
    @JsonIgnore
    public List<Actor> getShareholders(EconomyState state) {
        List<Actor> holders = new ArrayList<>();

        for (Actor a : state.getActors()) {
            if (a.getAssets().has(new Stock(stockId))) {
                holders.add(a);
            }
        }

        holders.sort((h1, h2) -> {
            long c1 = Objects.requireNonNull(h1.getAssets().get(new Stock(stockId))).getQuantity();
            long c2 = Objects.requireNonNull(h2.getAssets().get(new Stock(stockId))).getQuantity();

            return Long.compare(c2, c1);
        });

        return holders;
    }

    @Override
    @JsonIgnore
    public List<Actor> getMajorityShareholders(EconomyState state) {
        List<Actor> holders = new ArrayList<>();

        long highestShareCount = -1L;

        // List is already sorted
        for (Actor a : getShareholders(state)) {

            // Deduct self owned shares
            if (!a.equals(this)) {

                long q = Objects.requireNonNull(a.getAssets().get(new Stock(stockId))).getQuantity();
                if (q > highestShareCount) {
                    // First majority shareholder
                    highestShareCount = q;
                    holders.add(a);
                } else if (q == highestShareCount) {
                    // Equal shares as first majority shareholder
                    holders.add(a);
                } else {
                    // Not a majority shareholder; break
                    break;
                }

            }
        }

        return holders;
    }

    @JsonProperty
    private final Type type = Type.TRUST;

    @Override
    @JsonIgnore
    public Type getType() {
        return type;
    }
}
