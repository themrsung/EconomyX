package oasis.economyx.classes.actor.sovereignty;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.EconomicActor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.index.qual.NonNegative;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * The instantiable class of Sovereign
 */
public abstract class Sovereignty extends EconomicActor implements Sovereign {
    public Sovereignty(UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(uniqueId, name);

        this.representative = null;
        this.representativePay = new CashStack(currency, 0L);
        this.protectionFee = new CashStack(currency, 0L);

        this.incomeTaxRate = 0f;
        this.interestTaxRate = 0f;
        this.dividendTaxRate = 0f;
    }

    public Sovereignty() {
        this.representative = null;
        this.representativePay = null;
        this.protectionFee = null;

        this.incomeTaxRate = 0f;
        this.interestTaxRate = 0f;
        this.dividendTaxRate = 0f;
    }

    public Sovereignty(Sovereignty other) {
        super(other);

        this.representative = other.representative;
        this.representativePay = other.representativePay;
        this.protectionFee = other.protectionFee;

        this.incomeTaxRate = other.incomeTaxRate;
        this.interestTaxRate = other.interestTaxRate;
        this.dividendTaxRate = other.dividendTaxRate;
    }

    @Nullable
    @JsonProperty
    @JsonIdentityReference
    private Person representative;

    @NonNull
    @JsonProperty
    private CashStack representativePay;

    @NonNull
    @JsonProperty
    private CashStack protectionFee;

    @NonNegative
    @JsonProperty
    private float incomeTaxRate;

    @NonNegative
    @JsonProperty
    private float interestTaxRate;

    @NonNegative
    @JsonProperty
    private float dividendTaxRate;

    @Override
    @JsonIgnore
    public @Nullable Person getRepresentative() {
        return representative;
    }

    @Override
    @JsonIgnore
    public void setRepresentative(@Nullable Person representative) {
        this.representative = representative;
    }

    @NonNull
    @Override
    @JsonIgnore
    public CashStack getRepresentativePay() {
        return new CashStack(representativePay);
    }

    @Override
    @JsonIgnore
    public void setRepresentativePay(@NonNull CashStack representativePay) {
        this.representativePay = representativePay;
    }


    @NonNull
    @Override
    @JsonIgnore
    public CashStack getProtectionFee() {
        return new CashStack(protectionFee);
    }

    @Override
    @JsonIgnore
    public void setProtectionFee(@NonNull CashStack protectionFee) {
        this.protectionFee = protectionFee;
    }

    @Override
    @JsonIgnore
    public @NonNegative float getIncomeTaxRate() {
        return incomeTaxRate;
    }

    @Override
    @JsonIgnore
    public @NonNegative float getInterestTaxRate() {
        return interestTaxRate;
    }

    @Override
    @JsonIgnore
    public @NonNegative float getDividendTaxRate() {
        return dividendTaxRate;
    }

    @Override
    @JsonIgnore
    public void setIncomeTaxRate(@NonNegative float rate) {
        incomeTaxRate = rate;
    }

    @Override
    @JsonIgnore
    public void setInterestTaxRate(@NonNegative float rate) {
        interestTaxRate = rate;
    }

    @Override
    @JsonIgnore
    public void setDividendTaxRate(@NonNegative float rate) {
        dividendTaxRate = rate;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        if (representative != null) {
            for (Person p : state.getPersons()) {
                if (p.getUniqueId().equals(representative.getUniqueId())) {
                    representative = p;
                    break;
                }
            }
        }
    }
}
