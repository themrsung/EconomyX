package oasis.economyx.classes.actor.company.finance;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.ActorType;
import oasis.economyx.interfaces.actor.types.finance.Credible;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.guarantee.Guarantee;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Guarantor extends Company implements Credible {
    /**
     * Creates a new guarantor
     * @param uniqueId Unique ID of this guarantor
     * @param name Name of this guarantor (not unique)
     * @param stockId ID of this guarantor's stock
     * @param shareCount Initial share count
     * @param currency Currency to use
     */
    public Guarantor(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.guarantees = new ArrayList<>();
    }

    public Guarantor() {
        super();

        this.guarantees = new ArrayList<>();
    }

    public Guarantor(Guarantor other) {
        super(other);
        this.guarantees = other.guarantees;
    }

    private final List<Guarantee> guarantees;

    @NotNull
    @Override
    public List<Guarantee> getGuarantees() {
        return new ArrayList<>(guarantees);
    }

    @Override
    public void addGuarantee(@NonNull Guarantee guarantee) {
        guarantees.add(guarantee);
    }

    @Override
    public void removeGuarantee(@NonNull Guarantee guarantee) {
        guarantees.remove(guarantee);
    }

    @JsonProperty
    private final ActorType type = ActorType.GUARANTOR;

    @Override
    @JsonIgnore
    public @NonNull ActorType getType() {
        return type;
    }
}
