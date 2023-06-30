package oasis.economyx.interfaces.actor.types.services;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * A protector is capable of providing protection to properties
 */
public interface PropertyProtector extends Actor {
    /**
     * Protection fee is charged on a per-interaction basis.
     * Charged once per every cancelled interaction by an unauthorized player.
     * Will not protect properties of insolvent clients.
     *
     * @return Protection fee per interaction.
     */
    @NonNull
    CashStack getProtectionFee();

    void setProtectionFee(@NonNull CashStack fee);
}