package oasis.economyx.types.security;

/**
 * Type with sensitive information that must be censored to outside viewers.
 */
public interface Sensitive {
    /**
     * Nukes sensitive data.
     * Do NOT call from within EconomyX.
     */
    void nuke();
}
