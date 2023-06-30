package oasis.economyx.events.contract;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.contract.Contract;
import oasis.economyx.types.asset.contract.ContractStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class ContractExpiredEvent extends ContractEvent {
    public ContractExpiredEvent(@NonNull ContractStack contract, @NonNull Actor holder) {
        super(contract, holder);
    }
}
