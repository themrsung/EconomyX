package oasis.economyx.events.contract;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.contract.ContractStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Forgives a contract.
 */
public final class ContractForgivenEvent extends ContractEvent {
    /**
     * Creates a new contract forgiven event.
     * @param contract Contract to forgive
     * @param holder Holder forgiving the contract
     * @param force Whether to force the forgiving of unforgivable contracts
     */
    public ContractForgivenEvent(@NonNull ContractStack contract, @NonNull Actor holder, boolean force) {
        super(contract, holder);
        this.force = force;
    }

    private boolean force;

    public boolean force() {
        return force;
    }

    public void setForce(boolean force) {
        this.force = force;
    }
}
