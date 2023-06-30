package oasis.economyx.interfaces.actor.types.governance;

import oasis.economyx.events.payment.PaymentEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.types.asset.cash.CashStack;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

/**
 * A representable actor requires a person to execute economic actions
 */
public interface Representable extends Actor {
    /**
     * Gets the person with authority to execute actions on this actor's behalf
     * @return Current representative
     */
    @Nullable
    Person getRepresentative();

    /**
     * Sets the representative of this actor
     * @param representative New representative
     */
    void setRepresentative(@Nullable Person representative);

    /**
     * Gets the hourly representative pay
     * @return Hourly pay
     */
    @NonNull
    CashStack getRepresentativePay();

    /**
     * Sets the hourly representative pay
     * @param pay Hourly pay
     */
    void setRepresentativePay(@NonNull CashStack pay);

    /**
     * Pays the representative. Called every hour.
     */
    default void payRepresentative() {
        if (getRepresentative() == null) return;

        Bukkit.getPluginManager().callEvent(new PaymentEvent(
                this,
                getRepresentative(),
                getRepresentativePay(),
                PaymentEvent.Cause.REPRESENTATIVE_PAYMENT
        ));
    }
}
