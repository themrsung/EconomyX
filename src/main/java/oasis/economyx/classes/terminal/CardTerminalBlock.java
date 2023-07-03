package oasis.economyx.classes.terminal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.terminal.CardTerminal;
import oasis.economyx.state.EconomyState;
import oasis.economyx.types.address.Address;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.beans.ConstructorProperties;

/**
 * A card terminal block.
 */
public final class CardTerminalBlock implements CardTerminal {
    /**
     * Creates a new card terminal block.
     *
     * @param address Address of this block
     * @param seller  Seller who receives payments from this terminal
     * @param price   Price-per-click of this terminal
     */
    public CardTerminalBlock(@NonNull Address address, @NonNull CardAcceptor seller, @NonNull CashStack price) {
        this.address = address;
        this.seller = seller;
        this.price = price;
    }

    public CardTerminalBlock(CardTerminalBlock other) {
        this.address = other.address;
        this.seller = other.seller;
        this.price = other.price;
    }

    @NonNull
    @JsonProperty
    private final Address address;

    @NonNull
    @JsonProperty
    private CardAcceptor seller;

    @NonNull
    @JsonProperty
    private CashStack price;

    @Override
    @JsonIgnore
    public @NonNull Address getAddress() {
        return address;
    }

    @Override
    @JsonIgnore
    public @NonNull CardAcceptor getSeller() {
        return seller;
    }

    @Override
    @JsonIgnore
    public @NonNull CashStack getPrice() {
        return new CashStack(price);
    }

    @ConstructorProperties({"address", "seller", "price"})
    public CardTerminalBlock() {
        this.address = null;
        this.seller = null;
        this.price = null;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        for (CardAcceptor orig : state.getCardAccpetors()) {
            if (orig.getUniqueId().equals(seller.getUniqueId())) {
                seller = orig;
                break;
            }
        }
    }
}
