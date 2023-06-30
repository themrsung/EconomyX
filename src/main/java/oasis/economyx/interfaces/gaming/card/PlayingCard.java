package oasis.economyx.interfaces.gaming.card;

import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A playing card is an immutable and unique object.
 * Playing cards are not designed to be copied.
 */
public interface PlayingCard {
    /**
     * Gets one playing card by shape and number
     * @param shape Shape of card
     * @param number Number of card
     * @return New instance
     */
    static PlayingCard get(Shape shape, Number number) {
        return new PlayableCard(UUID.randomUUID(), shape, number);
    }

    /**
     * Gets the unique ID of this playing card.
     * Cards are unique for security reasons.
     * @return Unique ID
     */
    UUID uniqueId();

    /**
     * Gets the shape of this card.
     * @return Shape
     */
    Shape shape();

    /**
     * Gets the number denoted on this card.
     * @return Number
     */
    Number number();

    enum Number {
        ACE,
        DEUCE,
        THREE,
        FOUR,
        FIVE,
        SIX,
        SEVEN,
        EIGHT,
        NINE,
        TEN,
        JACK,
        QUEEN,
        KING;

        /**
         * Gets the numeric value of this card.
         * Ace is considered to be highest.
         * @return Numeric value
         */
        public int getValue() {
            return switch (this) {
                case ACE -> 14;
                case DEUCE -> 2;
                case THREE -> 3;
                case FOUR -> 4;
                case FIVE -> 5;
                case SIX -> 6;
                case SEVEN -> 7;
                case EIGHT -> 8;
                case NINE -> 9;
                case TEN -> 10;
                case JACK -> 11;
                case QUEEN -> 12;
                case KING -> 13;
            };
        }

        /**
         * Cheks if this number is 1 level higher than the given number.
         * @param number Number to check
         * @return Whether this number continues from the given number
         */
        public boolean continues(Number number) {
            return switch (this) {
                case ACE -> number == KING;
                case KING -> number == QUEEN;
                case QUEEN -> number == JACK;
                case JACK -> number == TEN;
                case TEN -> number == NINE;
                case NINE -> number == EIGHT;
                case EIGHT -> number == SEVEN;
                case SEVEN -> number == SIX;
                case SIX -> number == FIVE;
                case FIVE -> number == FOUR;
                case FOUR -> number == THREE;
                case THREE -> number == DEUCE;
                case DEUCE -> number == ACE;
            };
        }
    }

    enum Shape {
        SPADES,
        DIAMONDS,
        CLOVER,
        HEARTS;

        public boolean isRed() {
            return switch (this) {
                case DIAMONDS, HEARTS -> true;
                default -> false;
            };
        }
    }

    record PlayableCard(@NonNull UUID uniqueId, Shape shape, Number number) implements PlayingCard {
        public Shape getShape() {
            return shape;
        }

        public Number getNnumber() {
            return number;
        }
    }
}
