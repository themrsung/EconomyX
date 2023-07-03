package oasis.economyx.interfaces.terminal;

import com.fasterxml.jackson.annotation.JsonIgnore;
import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.types.address.Address;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface CardTerminal extends References {
    /**
     * Do not change this after deployment.
     */
    @NonNull
    @JsonIgnore
    Material TERMINAL_ITEM = Material.COMMAND_BLOCK;

    /**
     * Gets the location of this terminal.
     *
     * @return Address
     */
    @NonNull
    @JsonIgnore
    Address getAddress();

    /**
     * Gets the seller who gets paid from this terminal.
     *
     * @return Seller
     */
    @NonNull
    @JsonIgnore
    CardAcceptor getSeller();

    /**
     * Gets the price-per-click of this terminal.
     *
     * @return Price
     */
    @NonNull
    @JsonIgnore
    CashStack getPrice();
}
