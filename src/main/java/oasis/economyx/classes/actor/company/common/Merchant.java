package oasis.economyx.classes.actor.company.common;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.terminal.CardTerminal;
import oasis.economyx.types.asset.cash.Cash;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public final class Merchant extends Company implements CardAcceptor {
    /**
     * Creates a new merchant
     *
     * @param uniqueId   Unique ID of this merchant
     * @param name       Name of this merchant (not unique)
     * @param stockId    ID of this merchant's stock
     * @param shareCount Initial share count
     * @param currency   Currency to use
     */
    public Merchant(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.cardTerminals = new ArrayList<>();
    }

    public Merchant() {
        super();

        this.cardTerminals = new ArrayList<>();
    }

    public Merchant(Merchant other) {
        super(other);

        this.cardTerminals = other.cardTerminals;
    }

    @NonNull
    private final List<CardTerminal> cardTerminals;

    @NonNull
    @Override
    public List<CardTerminal> getCardTerminals() {
        return new ArrayList<>(cardTerminals);
    }

    @Override
    public void addCardTerminal(@NonNull CardTerminal terminal) {
        cardTerminals.add(terminal);
    }

    @Override
    public void removeCardTerminal(@NonNull CardTerminal terminal) {
        cardTerminals.remove(terminal);
    }

    @JsonProperty
    private final Type type = Type.MERCHANT;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
