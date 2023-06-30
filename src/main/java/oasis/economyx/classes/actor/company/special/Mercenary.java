package oasis.economyx.classes.actor.company.special;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.services.Faction;
import oasis.economyx.interfaces.actor.types.services.Protector;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.UUID;

/**
 * aka PMC
 */
public final class Mercenary extends Company implements Faction, Protector {
    /**
     * Creates a new mercenary
     *
     * @param uniqueId   Unique ID of this mercenary
     * @param name       Name of this mercenary (not unique)
     * @param stockId    ID of this mercenary's stock
     * @param shareCount Initial share count
     * @param currency   Currency to use
     */
    public Mercenary(UUID uniqueId, @Nullable String name, UUID stockId, long shareCount, Cash currency) {
        super(uniqueId, name, stockId, shareCount, currency);

        this.protectionFee = new CashStack(currency, 0L);
    }

    public Mercenary() {
        super();

        this.protectionFee = null;
    }

    public Mercenary(Mercenary other) {
        super(other);

        this.protectionFee = other.protectionFee;
    }

    private CashStack protectionFee;

    @Override
    public @NonNull CashStack getProtectionFee() {
        return new CashStack(protectionFee);
    }

    @Override
    public void setProtectionFee(@NonNull CashStack fee) {
        this.protectionFee = fee;
    }

    @JsonProperty
    private final Type type = Type.MERCENARY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
