package oasis.economyx.interfaces.actor.types.manufacturing;

import oasis.economyx.interfaces.actor.types.institutional.Institutional;

/**
 * A scientific institution can develop and produce high-end technology
 * Requires a mod to add the special items
 * ALl items require both time and resources to create
 *
 * <ul>
 *     <li>
 *         Missiles: can be launched to a certain coordinate carrying a payload within the same world, and with limited range
 *     </li>
 *     <li>
 *         ICBMs: can be launched to any world, and has no range limit
 *     </li>
 *         Rockets: can be used to launch satellites into orbit
 *     </li>
 *     <li>
 *         Satellites: provides intelligence to the parent sovereign of this institution
 *     </li>
 *     <li>
 *         Nuclear Weapons: decimates anything within a large radius when detonated; Can be used as a payload for missiles and ICBMs
 *     </li>
 *     <li>
 *         Point Defense Systems: one-time use installment to prevent a nuclear attack
 *     </li>
 * </ul>
 */
public interface Scientific extends Institutional {
    // TODO
}
