package oasis.economyx.interfaces.actor.types.institutional;

import oasis.economyx.interfaces.physical.Banknote;
import org.bukkit.inventory.ItemStack;

import java.util.List;

/**
 * A banknote issuer can issue physical notes
 * Note that banknotes cannot be unissued (even if somehow the currency has been deleted, which is not supported in the first place)
 */
public interface BanknoteIssuer extends Institutional {
    /**
     * Gets a list of all issued banknotes
     *
     * @return A copied list of banknotes
     */
    List<Banknote> getIssuedBanknotes();

    /**
     * Issues a new banknote
     *
     * @param note Note to issue
     * @return The physical item that can be given to a player
     * @throws IllegalArgumentException When the issuer has insufficient cash, parent sovereign does not have a currency, or the denotation is different
     */
    ItemStack issueBanknote(Banknote note) throws IllegalArgumentException;
}
