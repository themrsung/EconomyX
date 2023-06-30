package oasis.economyx.interfaces.gaming.player;

import oasis.economyx.classes.actor.person.NaturalPerson;
import oasis.economyx.interfaces.gaming.card.Deck;
import oasis.economyx.types.asset.chip.ChipStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * Represents a player seated at a card table
 */
public interface CardPlayer extends TablePlayer {
    /**
     * Gets a new card player.
     * @param person Person playing this game
     * @param entry Initial buy-in
     * @return Card player instance
     */
    static CardPlayer get(@NonNull NaturalPerson person, @NonNull ChipStack entry) {
        return new SeatedCardPlayer(person.getUniqueId(), entry, Deck.getEmptyDeck());
    }

    /**
     * Gets the hand of this player. (Cards player is holding)
     * @return Hand
     */
    @NonNull
    Deck getHand();

    record SeatedCardPlayer(@NonNull UUID playerId, @NonNull ChipStack stack, @NonNull Deck hand) implements CardPlayer {
        @Override
        public @NonNull UUID getPlayerId() {
            return playerId;
        }

        @Override
        public @NonNull ChipStack getStack() {
            return null;
        }

        @Override
        public @NonNull Deck getHand() {
            return hand;
        }
    }
}
