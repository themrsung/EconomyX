package oasis.economyx.asset.contract.option;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.actor.Actor;
import oasis.economyx.asset.AssetStack;
import oasis.economyx.asset.AssetType;
import oasis.economyx.asset.contract.Contract;
import oasis.economyx.asset.contract.note.Note;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.util.UUID;

public final class Option implements Contract {
    public Option() {
        this.uniqueId = UUID.randomUUID();
        this.counterparty = null;
        this.delivery = null;
        this.expiry = new DateTime();
        this.optionType = null;
    }

    public Option(@NonNull UUID uniqueId, @NonNull Actor counterparty, @NonNull AssetStack delivery, @Nullable DateTime expiry, @NonNull OptionType optionType) {
        this.uniqueId = uniqueId;
        this.counterparty = counterparty;
        this.delivery = delivery;
        this.expiry = expiry;
        this.optionType = optionType;
    }

    public Option(Option other) {
        this.uniqueId = other.uniqueId;
        this.counterparty = other.counterparty;
        this.delivery = other.delivery;
        this.expiry = other.expiry;
        this.optionType = other.optionType;
    }

    @NonNull
    private final UUID uniqueId;
    @NonNull
    private final Actor counterparty;

    @NonNull
    private final AssetStack delivery;

    @Nullable
    private final DateTime expiry;

    @NonNull
    @JsonProperty("optionType")
    private final OptionType optionType;

    @NonNull
    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public @NonNull AssetType getType() {
        return AssetType.OPTION;
    }

    @NonNull
    @Override
    public AssetStack getDelivery() {
        return delivery;
    }

    @Nullable
    @Override
    public DateTime getExpiry() {
        return expiry;
    }

    @NonNull
    @Override
    public Actor getCounterparty() {
        return counterparty;
    }

    @NonNull
    @JsonIgnore
    public OptionType getOptionType() {
        return optionType;
    }
}
