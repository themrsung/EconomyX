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
     * Gets an empty deck of cards.
     * @return Empty deck
     */
    static Deck getEmptyDeck() {
        return new PlayableDeck(new ArrayList<>());
    }

    /**
     * Gets a standard deck of 52 cards.
     * @return Deck
     */
    static Deck getDefaultDeck() {
        List<PlayingCard> cards = new ArrayList<>();

        for (PlayingCard.Number num : PlayingCard.Number.values()) {
            for (PlayingCard.Shape shape : PlayingCard.Shape.values()) {
                cards.add(PlayingCard.get(shape, num));
            }
        }

        return new PlayableDeck(cards);
    }

    /**
     * Gets a deck of multiple standard decks.
     * @param num Number of decks to get
     * @return
     */
    static Deck getExtendedDeck(int num) {
        Deck deck = getEmptyDeck();

        for (int i = 0; i < num; i++) {
            deck.add(getDefaultDeck());
        }

        return deck;
    }

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
     * Adds a deck of cards to this deck.
     * @param deck Deck to add
     * @throws IllegalArgumentException When at least one of the cards is already in this deck
     */
    void add(Deck deck) throws IllegalArgumentException;

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

        @Override
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
        public void add(Deck deck) throws IllegalArgumentException {
            for (PlayingCard card : deck.getCards()) {
                add(card);
            }
        }

        @Override
        public void remove(PlayingCard card) throws IllegalArgumentException {
            if (!cards.remove(card)) throw new IllegalArgumentException();
        }
    }
}
