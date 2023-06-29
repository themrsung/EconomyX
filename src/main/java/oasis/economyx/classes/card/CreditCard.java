package oasis.economyx.classes.card;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.CardIssuer;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.interfaces.card.CardType;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;
import org.joda.time.DateTime;
import org.spongepowered.api.Sponge;

import java.util.UUID;

/**
 * A credit card has a limit and balance
 */
public final class CreditCard implements Card {
    /**
     * Creates a new credit card
     * @param uniqueId Unique ID of this card
     * @param issuer Issuer of this card
     * @param holder Intended holder of this card
     * @param limit Limit of this card
     * @param expiry Expiry of this card (null for perpetual cards)
     */
    public CreditCard(@NonNull UUID uniqueId, @NonNull CardIssuer issuer, @NonNull Actor holder, @NonNull CashStack limit, @Nullable DateTime expiry) {
        this.uniqueId = uniqueId;
        this.issuer = issuer;
        this.holder = holder;
        this.limit = limit;
        this.balance = new CashStack(limit.getAsset(), 0L);
        this.expiry = expiry;
    }

    public CreditCard() {
        this.uniqueId = UUID.randomUUID();
        this.issuer = null;
        this.holder = null;
        this.limit = null;
        this.balance = null;
        this.expiry = null;
    }

    public CreditCard(CreditCard other) {
        this.uniqueId = other.uniqueId;
        this.issuer = other.issuer;
        this.holder = other.holder;
        this.limit = other.limit;
        this.balance = other.balance;
        this.expiry = other.expiry;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final CardIssuer issuer;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private final Actor holder;

    @NonNull
    @JsonProperty
    private final CashStack limit;

    @NonNull
    @JsonProperty
    private CashStack balance;

    @Nullable
    @JsonProperty
    private final DateTime expiry;

    @Override
    @JsonIgnore
    public @NonNull UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    @JsonIgnore
    public @NonNull CardIssuer getIssuer() {
        return issuer;
    }

    @Override
    @JsonIgnore
    public @NonNull Actor getHolder() {
        return holder;
    }

    @Override
    @JsonIgnore
    public @NonNull CashStack getPayable() {
        return limit.subtract(balance);
    }

    @NonNull
    @JsonIgnore
    public CashStack getBalance() {
        return balance;
    }

    @Override
    @JsonIgnore
    public @Nullable DateTime getExpiry() {
        return expiry;
    }

    @Override
    @JsonIgnore
    public @NonNull CashStack onUsed(@NonNull CardAcceptor seller, @NonNull CashStack amount) throws IllegalArgumentException {
        if (getPayable().isSmallerThan(amount)) throw new IllegalArgumentException();

        Sponge.eventManager().post(new PaymentEvent(
                issuer,
                seller,
                amount,
                null, // TODO
                false
        ));

        this.balance = balance.add(amount);
        return getPayable();
    }

    @NonNull
    @JsonProperty
    private final CardType type = CardType.CREDIT_CARD;

    @NotNull
    @Override
    @JsonIgnore
    public CardType getType() {
        return type;
    }

    @JsonIgnore
    public void onSettled() {
        Sponge.eventManager().post(new PaymentEvent(
                holder,
                issuer,
                balance,
                null, // TODO
                false
        ));

        this.balance.setQuantity(0L);
    }

    @Override
    @JsonIgnore
    public void onExpired() {
        onSettled();

        issuer.cancelCard(this);
    }
}
