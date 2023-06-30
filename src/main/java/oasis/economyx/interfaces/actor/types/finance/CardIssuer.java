package oasis.economyx.interfaces.actor.types.finance;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.card.Card;
import org.bukkit.inventory.ItemStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

public interface CardIssuer extends Actor {
    /**
     * Gets every card this actor has issued.
     *
     * @return Issued cards
     */
    @NonNull
    List<Card> getIssuedCards();

    /**
     * Issues a new card, and returns the physical item to be given.
     *
     * @param card Card to issue
     * @return Physical card that can be given out
     */
    ItemStack issueCard(@NonNull Card card);

    /**
     * Invalidates a card. Does not remove the physical card from existence.
     *
     * @param card Card to remove
     */
    void cancelCard(@NonNull Card card);
}
