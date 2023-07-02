package oasis.economyx.events.contract;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.contract.option.OptionStack;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class OptionEvent extends ContractEvent {
    public OptionEvent(@NonNull OptionStack contract, @NonNull Actor holder) {
        super(contract, holder);
    }

    @Override
    public @NonNull OptionStack getContract() {
        if (!(super.getContract() instanceof OptionStack)) throw new RuntimeException();
        return (OptionStack) super.getContract();
    }
}
