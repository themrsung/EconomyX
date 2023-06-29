package oasis.economyx.actor.types.manufacturing;

import oasis.economyx.actor.types.institutional.Institutional;

/**
 * A scientific institution can develop and produce high-end technology
 * Requires a mod to add the special items
 * ALl items require both time and resources to create
 *
 * <ul>
 *     Missiles: can be launched to a certain coordinate carrying a payload within the same world, and with limited range
 *     ICBMs: can be launched to any world, and has no range limit
 *     Rockets: can be used to launch satellites into orbit
 *     Satellites: provides intelligence to the parent sovereign of this institution
 *     Nuclear Weapons: decimates anything within a large radius when detonated; Can be used as a payload for missiles and ICBMs
 *     Point Defense Systems: one-time use installment to prevent a nuclear attack
 * </ul>
 */
public interface Scientific extends Institutional {
    // TODO
}
