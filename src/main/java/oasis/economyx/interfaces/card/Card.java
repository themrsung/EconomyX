package oasis.economyx.interfaces.card;

import com.fasterxml.jackson.annotation.*;
import oasis.economyx.classes.card.CreditCard;
import oasis.economyx.classes.card.DebitCard;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.CardIssuer;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

/**
 * A card is a form of payment that is accepted by card acceptors.
 * This is given out as an in-game item, and requires a mod to function.
 * Anyone physically holding the card can use it, regardless of whether they are or represent the holder.
 */

@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = CreditCard.class, name = "CREDIT_CARD"),
        @JsonSubTypes.Type(value = DebitCard.class, name = "DEBIT_CARD")
})
public interface Card extends References {
    /**
     * Do not change this after deployment
     */
    @JsonIgnore
    Material CARD_ITEM = Material.BOOK;

    /**
     * Gets the unique ID of this card.
     *
     * @return Unique ID
     */
    @NonNull
    @JsonIgnore
    UUID getUniqueId();

    /**
     * Gets the issuer of this card.
     *
     * @return Issuer
     */
    @NonNull
    @JsonIgnore
    CardIssuer getIssuer();

    /**
     * Gets the official holder of this card.
     *
     * @return Holder
     */
    @NonNull
    @JsonIgnore
    Actor getHolder();

    /**
     * Gets the payable balance (remaining limit) of this card.
     *
     * @return Payable balance
     */
    @NonNull
    @JsonIgnore
    CashStack getPayable();

    /**
     * Gets the expiry of this card.
     * Set to null for a perpetual card.
     *
     * @return Expiry
     */
    @Nullable
    @JsonIgnore
    DateTime getExpiry();

    /**
     * Gets whether this card has been activated by its holder.
     *
     * @return Active
     */
    @JsonIgnore
    boolean isActive();

    /**
     * Sets the activation state of this card.
     *
     * @param active Active
     */
    @JsonIgnore
    void setActive(boolean active);

    /**
     * Called when this card is used.
     *
     * @param amount Amount used
     * @return The remaining balance post-transaction
     * @throws IllegalArgumentException When amount is greater than payable balance, or currencies are unequal
     */
    @NonNull
    @JsonIgnore
    CashStack onUsed(@NonNull CardAcceptor seller, @NonNull CashStack amount) throws IllegalArgumentException;

    /**
     * Called when this card has expired.
     */
    @JsonIgnore
    void onExpired();

    /**
     * Gets the card type
     *
     * @return Card type
     */
    @JsonIgnore
    Card.Type getType();

    enum Type {
        CREDIT_CARD,
        DEBIT_CARD
    }
}
