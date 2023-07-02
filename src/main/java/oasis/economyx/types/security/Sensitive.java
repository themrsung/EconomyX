package oasis.economyx.types.security;

import com.google.errorprone.annotations.DoNotCall;

/**
 * Type with sensitive information that must be censored to outside viewers.
 */
public interface Sensitive {
    /**
     * Nukes sensitive data.
     * Do NOT call from within EconomyX.
     */
    @DoNotCall("This is a destructive action. Do NOT call this unless you are willing to destroy all data.")
    void nuke();
}
