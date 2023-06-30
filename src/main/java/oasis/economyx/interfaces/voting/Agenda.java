package oasis.economyx.interfaces.voting;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.classes.voting.DummyAgenda;

/**
 * An agenda is a potential action executable on its parent candidates' winning of a vote.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes(
        @JsonSubTypes.Type(value = DummyAgenda.class, name = "DUMMY")
)
public interface Agenda extends Runnable {
    /**
     * Gets the type of this agenda.
     * @return Type
     */
    Type getType();
    enum Type {
        /**
         * An agenda that does nothing
         */
        DUMMY,

        /**
         * An agenda that changes the name of an actor
         */
        CHANGE_NAME,

        /**
         * An agenda that a hires a person as the representative of a representable
         */
        HIRE_REPRESENTATIVE,

        /**
         * An agenda that fires a representative of a representable
         */
        FIRE_REPRESENTATIVE,

        /**
         * An agenda that pays dividends in cash to a shared actor's shareholders
         */
        CASH_DIVIDEND,

        /**
         * An agenda that pays dividends in stock to a shard actor's shareholders
         */
        STOCK_DIVIDEND,

        // LIQUIDATE, - TODO

        /**
         * An agenda where a shared actor issues more of its stock
         */
        STOCK_ISSUE,

        /**
         * An agenda where a shared actor retires its self-owned stock
         */
        STOCK_RETIRE,

        /**
         * An agenda where a shared actor splits its stock
         */
        STOCK_SPLIT,

        /**
         * Agenda for a presidential candidate
         */
        MAKE_ME_PRESIDENT,

        /**
         * Agenda for a general election
         */
        MAKE_ME_SENATOR,
    }
}
