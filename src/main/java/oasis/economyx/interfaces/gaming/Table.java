package oasis.economyx.interfaces.gaming;

import com.fasterxml.jackson.annotation.JsonIdentityInfo;
import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import com.fasterxml.jackson.annotation.ObjectIdGenerators;
import oasis.economyx.classes.gaming.*;
import oasis.economyx.interfaces.actor.types.services.House;

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
     * @return Unique ID
     */
    UUID getUniqueId();

    /**
     * Gets the house running this table
     * @return Casino
     */
    House getCasino();

    /**
     * Gets the type of this game
     * @return Table type
     */
    TableType getType();
}
