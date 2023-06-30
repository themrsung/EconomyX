package oasis.economyx.classes.actor.institution.tripartite;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.institutional.Legislative;
import oasis.economyx.types.asset.cash.Cash;
import oasis.economyx.classes.actor.institution.Institution;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

/**
 * A sovereign can have multiple legislatures (e.g. Senate, Congress, Parliament, etc.)
 */
public final class Legislature extends Institution implements Legislative {
    /**
     * Creates a new legislature
     * @param parent Parent sovereign (cannot be null)
     * @param uniqueId Unique ID of this legislature
     * @param name Name of this legislature (not unique)
     * @param currency Currency of this legislature
     */
    public Legislature(@NonNull Sovereign parent, UUID uniqueId, @Nullable String name, @NonNull Cash currency) {
        super(parent, uniqueId, name, currency);

        this.laws = new ArrayList<>();
    }

    public Legislature() {
        super();

        this.laws = new ArrayList<>();
    }

    public Legislature(Legislature other) {
        super(other);
        this.laws = other.laws;
    }

    private final List<String> laws;

    @NotNull
    @Override
    public List<String> getLaws() {
        return new ArrayList<>(laws);
    }

    @Override
    public void passLaw(@NonNull String law) {
        laws.add(law);
    }

    @Override
    public void repealLaw(@NonNull String law) {
        laws.remove(law);
    }

    @JsonProperty
    private final Type type = Type.LEGISLATURE;

    @Override
    @JsonIgnore
    public Actor.Type getType() {
        return type;
    }
}
