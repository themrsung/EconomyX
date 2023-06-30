package oasis.economyx.interfaces.card;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import oasis.economyx.classes.card.CreditCard;
import oasis.economyx.classes.card.DebitCard;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.finance.CardIssuer;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
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
public interface Card {
    /**
     * Do not change this after deployment
     */
    Material CARD_ITEM = Material.BOOK;

    /**
     * Gets the unique ID of this card.
     * @return Unique ID
     */
    @NonNull
    UUID getUniqueId();

    /**
     * Gets the issuer of this card.
     * @return Issuer
     */
    @NonNull
    CardIssuer getIssuer();

    /**
     * Gets the official holder of this card.
     * @return Holder
     */
    @NonNull
    Actor getHolder();

    /**
     * Gets the payable balance (remaining limit) of this card.
     * @return Payable balance
     */
    @NonNull
    CashStack getPayable();

    /**
     * Gets the expiry of this card.
     * Set to null for a perpetual card.
     * @return Expiry
     */
    @Nullable
    DateTime getExpiry();

    /**
     * Called when this card is used.
     * @param amount Amount used
     * @return The remaining balance post-transaction
     * @throws IllegalArgumentException When amount is greater than payable balance, or currencies are unequal
     */
    @NonNull
    CashStack onUsed(@NonNull CardAcceptor seller, @NonNull CashStack amount) throws IllegalArgumentException;

    /**
     * Called when this card has expired.
     */
    void onExpired();

    /**
     * Gets the card type
     * @return Card type
     */
    Card.Type getType();

    enum Type {
        CREDIT_CARD,
        DEBIT_CARD
    }
}
