package oasis.economyx.interfaces.gaming.card;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A deck is a collection of cards with added functionality.
 */
public interface Deck {
    /**
     * Gets every card in this deck.
     * @return A copied list of cards
     */
    List<PlayingCard> getCards();

    /**
     * Gets whether this deck contains a certain card.
     * Unique ID must match to return true;
     * @param card Card to check
     * @return Whether this deck contains the card
     */
    boolean contains(PlayingCard card);

    /**
     * Sends a card to another deck.
     * @param destination Destination deck
     * @param card Card to send
     * @throws IllegalArgumentException When this deck does not contain the card
     */
    void send(Deck destination, PlayingCard card) throws IllegalArgumentException;

    /**
     * Adds a card to this deck.
     * @param card Card to add
     * @throws IllegalArgumentException When this deck already contains the card
     */
    void add(PlayingCard card) throws IllegalArgumentException;

    /**
     * Removes a card from this deck.
     * @param card Card to remove
     * @throws IllegalArgumentException When this deck does not contain the card
     */
    void remove(PlayingCard card) throws IllegalArgumentException;

    /**
     * Shuffles this deck
     */
    void shuffle();

    record PlayableDeck(@NonNull List<PlayingCard> cards) implements Deck {
        @Override
        public List<PlayingCard> getCards() {
            return new ArrayList<>(cards);
        }

        public void shuffle() {
            Collections.shuffle(cards);
        }

        @Override
        public boolean contains(PlayingCard card) {
            return cards.contains(card);
        }

        @Override
        public void send(Deck destination, PlayingCard card) throws IllegalArgumentException {
            if (!cards.remove(card)) throw new IllegalArgumentException();

            destination.add(card);
        }

        @Override
        public void add(PlayingCard card) {
            if (contains(card)) throw new IllegalArgumentException();

            cards.add(card);
        }

        @Override
        public void remove(PlayingCard card) throws IllegalArgumentException {
            if (!cards.remove(card)) throw new IllegalArgumentException();
        }
    }
}
