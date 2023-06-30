package oasis.economyx.interfaces.voting;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.classes.voting.MessageConsoleAgenda;

/**
 * An agenda is a potential action executable on its parent candidates' winning of a vote.
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)
@JsonSubTypes(
        @JsonSubTypes.Type(value = MessageConsoleAgenda.class, name = "MESSAGE_CONSOLE")
)
public interface Agenda extends Runnable {
    /**
     * Gets the type of this agenda.
     * @return Type
     */
    Type getType();
    enum Type {
        MESSAGE_CONSOLE
    }
}
