package oasis.economyx.interfaces.gaming.table;

import com.fasterxml.jackson.annotation.*;
import oasis.economyx.classes.gaming.table.*;
import oasis.economyx.interfaces.actor.types.gaming.House;
import oasis.economyx.types.asset.chip.ChipStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.UUID;

/**
 * A game is hosted by a house (Usually a casino)
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
@JsonSubTypes({
        @JsonSubTypes.Type(value = BaccaratTable.class, name = "BACCARAT"),
        @JsonSubTypes.Type(value = BlackjackTable.class, name = "BLACKJACK"),
        @JsonSubTypes.Type(value = PokerTable.class, name = "POKER"),
        @JsonSubTypes.Type(value = RouletteTable.class, name = "ROULETTE"),
        @JsonSubTypes.Type(value = SlotMachine.class, name = "SLOT_MACHINE"),
})
public interface Table {
    /**
     * Gets the unique ID of this table
     *
     * @return Unique ID
     */
    @NonNull
    @JsonIgnore
    UUID getUniqueId();

    /**
     * Gets the house running this table
     *
     * @return Casino
     */
    @NonNull
    @JsonIgnore
    House getCasino();

    /**
     * Gets the dealer's chips on this table
     *
     * @return Dealer's chips
     */
    @NonNull
    @JsonIgnore
    ChipStack getChips();

    /**
     * Called every second.
     * Handles the progression of the table.
     */
    @JsonIgnore
    void progressGame();

    /**
     * Gets the type of this game
     *
     * @return Table type
     */
    @JsonIgnore
    Type getType();

    enum Type {
        /**
         * Texas holdem
         */
        POKER,

        /**
         * Blackjack
         */
        BLACKJACK,

        /**
         * Baccarat
         */
        BACCARAT,

        /**
         * Roulette
         */
        ROULETTE,

        /**
         * Slot machine
         */
        SLOT_MACHINE
    }
}
