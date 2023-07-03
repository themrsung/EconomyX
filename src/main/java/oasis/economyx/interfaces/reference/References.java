package oasis.economyx.interfaces.reference;

import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Interface for types with references to other type instances.
 * References require initialization after loading.
 */
public interface References {
    /**
     * Initializes every reference within this type.
     */
    void initialize(@NonNull EconomyState state);
}
