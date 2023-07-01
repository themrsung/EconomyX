package oasis.economyx.classes.actor.company.special;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.classes.actor.company.Company;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.warfare.Faction;
import oasis.economyx.interfaces.actor.types.services.PropertyProtector;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.types.asset.cash.CashStack;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * aka PMC
 */
public final class Mercenary extends Company implements Faction, PropertyProtector {
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
        this.hostilities = new ArrayList<>();
    }

    public Mercenary() {
        super();

        this.protectionFee = null;
        this.hostilities = new ArrayList<>();
    }

    public Mercenary(Mercenary other) {
        super(other);

        this.protectionFee = other.protectionFee;
        this.hostilities = other.hostilities;
    }

    @NonNull
    @JsonProperty
    private CashStack protectionFee;

    @Override
    @JsonIgnore
    public @NonNull CashStack getProtectionFee() {
        return new CashStack(protectionFee);
    }

    @Override
    @JsonIgnore
    public void setProtectionFee(@NonNull CashStack fee) {
        this.protectionFee = fee;
    }

    @NonNull
    @JsonProperty
    @JsonIdentityReference
    private List<Faction> hostilities;

    @NonNull
    @Override
    @JsonIgnore
    public List<Faction> getHostilities() {
        return new ArrayList<>(hostilities);
    }

    @Override
    @JsonIgnore
    public void addHostility(@NonNull Faction faction) {
        this.hostilities.add(faction);
    }

    @Override
    @JsonIgnore
    public void removeHostility(@NonNull Faction faction) {
        this.hostilities.remove(faction);
    }

    @JsonProperty
    private final Type type = Type.MERCENARY;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
