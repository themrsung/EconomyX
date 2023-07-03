package oasis.economyx.interfaces.actor.types.services;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.terminal.CardTerminal;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * A card acceptor can accept credit card payments
 */
public interface CardAcceptor extends Actor {
    /**
     * Gets every terminal this seller has installed.
     *
     * @return A copied list of terminals
     */
    @NonNull
    @JsonIgnore
    List<CardTerminal> getCardTerminals();

    /**
     * Associates a terminal with this seller.
     *
     * @param terminal Terminal to add
     */
    @JsonIgnore
    void addCardTerminal(@NonNull CardTerminal terminal);

    /**
     * Disassociates a terminal from this seller.
     *
     * @param terminal Terminal to remove
     */
    @JsonIgnore
    void removeCardTerminal(@NonNull CardTerminal terminal);
}
