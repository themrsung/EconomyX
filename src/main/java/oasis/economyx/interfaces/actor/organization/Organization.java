package oasis.economyx.interfaces.actor.organization;

import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import org.checkerframework.checker.nullness.qual.NonNull;

import java.util.List;

/**
 * An organization is a group of actor with a representing person
 * @param <M> Type of member
 */
public interface Organization<M extends Actor> extends Representable {
    /**
     * Gets all members in this organization
     * @return A copied list of members
     */
    @NonNull
    List<M> getMembers();

    /**
     * Adds a member to this organization
     * @param member Member to add
     */
    void addMember(@NonNull M member);

    /**
     * Removes a member from this organization
     * @param member Member to remove
     */
    void removeMember(@NonNull M member);
}
