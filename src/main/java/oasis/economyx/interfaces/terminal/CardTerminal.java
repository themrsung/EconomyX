package oasis.economyx.interfaces.terminal;

import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Material;

public interface CardTerminal {
    /**
     * Do not change this after deployment.
     */
    static Material TERMINAL_ITEM = Material.COMMAND_BLOCK;

    /**
     * Gets the seller who gets paid from this terminal.
     * @return Seller
     */
    CardAcceptor getSeller();

    /**
     * Gets the price-per-click of this terminal.
     * @return Price
     */
    CashStack getPrice();
}
