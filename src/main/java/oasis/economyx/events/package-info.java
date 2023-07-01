/**
 * Events are used to process interactions without constantly having a reference to the central state.
 * They are also implemented as a security measure.
 * The end goal is to have the state not accessible to packages outside EconomyX.
 * An immutable copy state will be provided as a proxy for getters. (Requires a deep copy of the full state)
 */
package oasis.economyx.events;