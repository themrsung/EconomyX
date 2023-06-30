package oasis.economyx.interfaces.voting;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.classes.voting.common.ChangeNameAgenda;
import oasis.economyx.classes.voting.common.DummyAgenda;
import oasis.economyx.classes.voting.election.MakeMePresidentAgenda;
import oasis.economyx.classes.voting.election.MakeMeSenatorAgenda;
import oasis.economyx.classes.voting.representable.FireRepresentativeAgenda;
import oasis.economyx.classes.voting.representable.HireRepresentativeAgenda;
import oasis.economyx.classes.voting.stock.DividendAgenda;
import oasis.economyx.classes.voting.stock.StockIssueAgenda;
import oasis.economyx.classes.voting.stock.StockRetireAgenda;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * An agenda is a potential action executable on its parent candidates' winning of a vote.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes({
        @JsonSubTypes.Type(value = DummyAgenda.class, name = "DUMMY"),
        @JsonSubTypes.Type(value = ChangeNameAgenda.class, name = "CHANGE_NAME"),
        @JsonSubTypes.Type(value = HireRepresentativeAgenda.class, name = "HIRE_REPRESENTATIVE"),
        @JsonSubTypes.Type(value = FireRepresentativeAgenda.class, name = "FIRE_REPRESENTATIVE"),
        @JsonSubTypes.Type(value = DividendAgenda.class, name = "DIVIDEND"),
        @JsonSubTypes.Type(value = StockIssueAgenda.class, name = "STOCK_ISSUE"),
        @JsonSubTypes.Type(value = StockRetireAgenda.class, name = "STOCK_RETIRE"),
        @JsonSubTypes.Type(value = MakeMePresidentAgenda.class, name = "MAKE_ME_PRESIDENT"),
        @JsonSubTypes.Type(value = MakeMeSenatorAgenda.class, name = "MAKE_ME_SENATOR")
})
public interface Agenda extends Runnable {
    /**
     * Gets the semantic description of this agenda.
     *
     * @return Description
     */
    @NonNull
    String getDescription();

    /**
     * Gets the type of this agenda.
     *
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
         * An agenda that pays dividends to a shared actor's shareholders
         */
        DIVIDEND,

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
