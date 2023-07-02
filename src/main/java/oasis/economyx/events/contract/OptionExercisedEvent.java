package oasis.economyx.events.contract;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.contract.option.OptionStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Does not check if option is exercisable. Check before calling.
 */
public final class OptionExercisedEvent extends OptionEvent {
    public OptionExercisedEvent(@NonNull OptionStack contract, @NonNull Actor holder) {
        super(contract, holder);
    }
}
