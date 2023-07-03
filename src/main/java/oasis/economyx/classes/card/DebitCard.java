package oasis.economyx.classes.card;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.Banker;
import oasis.economyx.interfaces.actor.types.finance.CardIssuer;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.banking.Account;
import oasis.economyx.interfaces.card.Card;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * A debit card is linked to an account and has no arbitrary limit (is limited only by the account balance)
 */
public final class DebitCard implements Card {
    /**
     * Creates a new debit card
     *
     * @param uniqueId Unique ID of this card
     * @param issuer   Issuer of this card
     * @param account  Account linked to this card
     * @param expiry   Expiry of this card (set to null for a perpetual card)
     * @throws IllegalArgumentException When the given account is not a cash account
     */
    public DebitCard(@NonNull UUID uniqueId, @NonNull CardIssuer issuer, @NonNull Account account, @Nullable DateTime expiry) throws IllegalArgumentException {
        if (!((account.getContent()) instanceof CashStack)) throw new IllegalArgumentException();

        this.uniqueId = uniqueId;
        this.issuer = issuer;
        this.account = account;
        this.expiry = expiry;
        this.active = false;
    }

    public DebitCard() {
        this.uniqueId = UUID.randomUUID();
        this.issuer = null;
        this.account = null;
        this.expiry = null;
        this.active = false;
    }

    public DebitCard(DebitCard other) {
        this.uniqueId = other.uniqueId;
        this.issuer = other.issuer;
        this.account = other.account;
        this.expiry = other.expiry;
        this.active = other.active;
    }

    @NonNull
    @JsonProperty
    private final UUID uniqueId;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private CardIssuer issuer;

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private Account account;
    @Nullable
    @JsonProperty
    private final DateTime expiry;

    @JsonProperty
    private boolean active;

    @NonNull
    @Override
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @NonNull
    @Override
    @JsonIgnore
    public CardIssuer getIssuer() {
        return issuer;
    }

    @JsonIgnore
    @NonNull
    public Account getAccount() {
        return account;
    }

    @NonNull
    @Override
    @JsonIgnore
    public Actor getHolder() {
        return getAccount().getClient();
    }

    @Override
    @JsonIgnore
    public @NonNull CashStack getPayable() {
        try {
            return (CashStack) getAccount().getContent();
        } catch (ClassCastException e) {
            throw new RuntimeException();
        }
    }

    @Nullable
    @Override
    @JsonIgnore
    public DateTime getExpiry() {
        return expiry;
    }

    @Override
    @JsonIgnore
    public boolean isActive() {
        return active;
    }

    @Override
    @JsonIgnore
    public void setActive(boolean active) {
        this.active = active;
    }

    /**
     * Debit card usage is handled differently from credit cards.
     * First, the amount is withdrawn from the account by the holder.
     * Then, the holder pays the seller the amount.
     *
     * @param seller Seller who accepted this payment
     * @param amount Amount paid
     * @return Remaining balance of the account after withdrawal
     * @throws IllegalArgumentException When balance if insufficient or currency is different
     * @throws RuntimeException         When the issuer is not a banker (payment will not be processed)
     */
    @Override
    @JsonIgnore
    public @NonNull CashStack onUsed(@NonNull CardAcceptor seller, @NonNull CashStack amount) throws IllegalArgumentException, RuntimeException {
        if (getPayable().isSmallerThan(amount)) throw new IllegalArgumentException();

        if (!(issuer instanceof Banker)) throw new RuntimeException();

        account.withdraw(amount);

        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                getHolder(),
                seller,
                amount,
                PaymentEvent.Cause.DEBIT_CARD_PAYMENT
        ));

        return getPayable();
    }

    @JsonProperty
    private final Type type = Type.DEBIT_CARD;

    @Override
    @JsonIgnore
    public Card.Type getType() {
        return type;
    }

    @Override
    @JsonIgnore
    public void onExpired() {
        issuer.cancelCard(this);
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        for (CardIssuer orig : state.getCardIssuers()) {
            if (orig.getUniqueId().equals(issuer.getUniqueId())) {
                issuer = orig;
                break;
            }
        }

        for (Account orig : state.getAccounts()) {
            if (orig.getUniqueId().equals(account.getUniqueId())) {
                account = orig;
                break;
            }
        }
    }
}
