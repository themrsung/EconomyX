package oasis.economyx.interfaces.terminal;

import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.types.address.Address;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Material;
import org.checkerframework.checker.nullness.qual.NonNull;

public interface CardTerminal {
    /**
     * Do not change this after deployment.
     */
    @NonNull
    Material TERMINAL_ITEM = Material.COMMAND_BLOCK;

    /**
     * Gets the location of this terminal.
     *
     * @return Address
     */
    @NonNull
    Address getAddress();

    /**
     * Gets the seller who gets paid from this terminal.
     *
     * @return Seller
     */
    @NonNull
    CardAcceptor getSeller();

    /**
     * Gets the price-per-click of this terminal.
     *
     * @return Price
     */
    @NonNull
    CashStack getPrice();
}
