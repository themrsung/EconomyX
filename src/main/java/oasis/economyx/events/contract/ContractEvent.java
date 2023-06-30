package oasis.economyx.events.contract;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.contract.ContractStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Represents a contract event.
 */
public abstract class ContractEvent extends EconomyEvent {
    public ContractEvent(@NonNull ContractStack contract, @NonNull Actor holder) {
        this.contract = contract;
        this.holder = holder;
    }

    @NonNull
    private final ContractStack contract;

    @NonNull
    private final Actor holder;

    @NonNull
    public ContractStack getContract() {
        return contract;
    }

    @NonNull
    public Actor getHolder() {
        return holder;
    }
}
