package oasis.economyx.interfaces.gaming.card;

import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A deck is a collection of cards with added functionality.
 */
public interface Deck {
    /**
     * Gets an empty deck of cards.
     *
     * @return Empty deck
     */
    static Deck getEmptyDeck() {
        return new PlayableDeck(new ArrayList<>());
    }

    /**
     * Gets a standard deck of 52 cards.
     *
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
     *
     * @param num Number of decks to get
     * @return Extended deck
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
     *
     * @return A copied list of cards
     */
    List<PlayingCard> getCards();

    /**
     * Gets whether this deck contains a certain card.
     * Unique ID must match to return true;
     *
     * @param card Card to check
     * @return Whether this deck contains the card
     */
    boolean contains(PlayingCard card);

    /**
     * Sends a card to another deck.
     *
     * @param destination Destination deck
     * @param card        Card to send
     * @throws IllegalArgumentException When this deck does not contain the card, or the destination deck already has the card
     */
    void send(Deck destination, PlayingCard card) throws IllegalArgumentException;

    /**
     * Sends the first card in this deck to another deck.
     *
     * @param destination Destination deck
     */
    void sendFirst(Deck destination) throws RuntimeException;

    /**
     * Adds a card to this deck.
     *
     * @param card Card to add
     * @throws IllegalArgumentException When this deck already contains the card
     */
    void add(PlayingCard card) throws IllegalArgumentException;

    /**
     * Adds a deck of cards to this deck.
     *
     * @param deck Deck to add
     * @throws IllegalArgumentException When at least one of the cards is already in this deck
     */
    void add(Deck deck) throws IllegalArgumentException;

    /**
     * Removes a card from this deck.
     *
     * @param card Card to remove
     * @throws IllegalArgumentException When this deck does not contain the card
     */
    void remove(PlayingCard card) throws IllegalArgumentException;

    /**
     * Gets the size of this deck. (How many cards there are)
     *
     * @return Size
     */
    int size();

    /**
     * Shuffles this deck
     */
    void shuffle();

    /**
     * Gets an identical copy of this deck.
     *
     * @return Copied deck
     */
    Deck copy();

    // Poker

    /**
     * Gets the highest card by value. (PlayingCard.Number)
     * Returns null when size of deck is 0.
     *
     * @return Highest card
     */
    @Nullable
    PlayingCard getHighestCard();

    /**
     * Gets the hand value of this deck in poker context.
     *
     * @return Hand value
     */
    PokerHand getPokerValue();

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
        public void sendFirst(Deck destination) throws RuntimeException {
            if (size() < 1) throw new RuntimeException();

            send(destination, getCards().get(0));
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

        @Override
        public int size() {
            return cards.size();
        }

        @Override
        public Deck copy() {
            return new PlayableDeck(getCards());
        }

        @Override
        @Nullable
        public PlayingCard getHighestCard() {
            List<PlayingCard> cards = getCards();
            cards.sort((c1, c2) -> Integer.compare(c2.number().getValue(), c1.number().getValue()));

            return cards.size() > 0 ? cards.get(0) : null;
        }

        @Override
        public PokerHand getPokerValue() {
            if (size() == 0) return PokerHand.HIGH_CARD;

            int shapes = 1;
            int numbers = 1;

            int straight = 1;
            PlayingCard.Number highestStraight = null;

            PlayingCard.Number highestCard = null;

            for (PlayingCard c1 : getCards()) {
                int s = 1;
                int n = 1;

                for (PlayingCard c2 : getCards()) {
                    if (!c1.equals(c2)) {
                        if (c1.shape() == c2.shape()) s++;
                        if (c1.number() == c2.number()) n++;

                        if (c2.number().continues(c1.number())) {
                            straight++;
                            highestStraight = c2.number();
                        } else {
                            straight = 1;
                            highestStraight = null;
                        }

                        if (highestCard == null || c2.number().getValue() > highestCard.getValue()) {
                            highestCard = c2.number();
                        }
                    }
                }

                if (s > shapes) shapes = s;
                if (n > numbers) numbers = n;
            }

            final boolean isStraight = straight >= 5;
            final boolean isFlush = shapes >= 5;

            if (isStraight && isFlush) {
                if (highestCard == PlayingCard.Number.ACE) return PokerHand.ROYAL_STRAIGHT_FLUSH;
                else return PokerHand.STRAIGHT_FLUSH;
            }

            if (shapes == 4) return PokerHand.FOUR_OF_A_KIND;

            if (numbers >= 5) return PokerHand.FULL_HOUSE;

            if (shapes == 5) return PokerHand.FLUSH;

            if (isStraight) return PokerHand.STRAIGHT;

            if (shapes == 3) return PokerHand.THREE_OF_A_KIND;

            if (numbers == 4) return PokerHand.TWO_PAIR;

            if (numbers >= 2) return PokerHand.PAIR;

            return PokerHand.HIGH_CARD;
        }
    }

    // Poker

    enum PokerHand {
        HIGH_CARD,
        PAIR,
        TWO_PAIR,
        THREE_OF_A_KIND,
        STRAIGHT,
        FLUSH,
        FULL_HOUSE,
        FOUR_OF_A_KIND,
        STRAIGHT_FLUSH,
        ROYAL_STRAIGHT_FLUSH
    }
}
