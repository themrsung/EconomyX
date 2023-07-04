package oasis.economyx.types.offer;

import com.fasterxml.jackson.annotation.JsonIdentityReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import oasis.economyx.classes.actor.organization.corporate.Cartel;
import oasis.economyx.classes.actor.organization.international.Alliance;
import oasis.economyx.classes.actor.organization.personal.Party;
import oasis.economyx.events.organization.alliance.AllianceMemberAddedEvent;
import oasis.economyx.events.organization.cartel.CartelMemberAddedEvent;
import oasis.economyx.events.organization.party.PartyMemberAddedEvent;
import oasis.economyx.events.personal.employment.DirectorHiredEvent;
import oasis.economyx.events.personal.employment.EmployeeHiredEvent;
import oasis.economyx.events.personal.representable.RepresentativeHiredEvent;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.actor.corporation.Corporation;
import oasis.economyx.interfaces.actor.organization.Organization;
import oasis.economyx.interfaces.actor.person.Person;
import oasis.economyx.interfaces.actor.sovereign.Sovereign;
import oasis.economyx.interfaces.actor.types.employment.Employer;
import oasis.economyx.interfaces.actor.types.governance.Representable;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.state.EconomyState;
import org.bukkit.Bukkit;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;

import java.beans.ConstructorProperties;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;

@JsonSerialize(as = Offer.Invitation.class)
@JsonDeserialize(as = Offer.Invitation.class)
public interface Offer extends References {
    static Offer getNew(@NonNull Actor sender, @NonNull Actor recipient, @NonNull Type type) throws IllegalArgumentException {
        switch (type) {
            case EMPLOYEE_OFFER, DIRECTOR_OFFER -> {
                if (!(sender instanceof Employer && recipient instanceof Person)) throw new IllegalArgumentException();
            }
            case REPRESENTATIVE_OFFER -> {
                if (!(sender instanceof Representable && recipient instanceof Person))
                    throw new IllegalArgumentException();
            }
            case MEMBERSHIP_OFFER -> {
                if (!(sender instanceof Organization<?>)) throw new IllegalArgumentException();
            }
        }

        return new Invitation(UUID.randomUUID(), sender, recipient, type);
    }

    @NonNull
    @JsonIgnore
    UUID getUniqueId();

    @NonNull
    @JsonIgnore
    Actor getSender();

    @NonNull
    @JsonIgnore
    Actor getRecipient();

    @NonNull
    @JsonIgnore
    Type getType();

    /**
     * Called when this offer is accepted.
     *
     * @throws RuntimeException When actors' signature do not match the offer type's required signature
     */
    @JsonIgnore
    void onAccepted() throws RuntimeException;

    /**
     * Called when this offer is declined.
     */
    @JsonIgnore
    void onDeclined(@NonNull EconomyState state);

    enum Type {
        /**
         * Employer to person
         */
        EMPLOYEE_OFFER,

        /**
         * Employer to person
         */
        DIRECTOR_OFFER,

        /**
         * Representable to person
         */
        REPRESENTATIVE_OFFER,

        /**
         * Organization(T) to T
         */
        MEMBERSHIP_OFFER;

        private static final List<String> K_EMPLOYEE_OFFER = Arrays.asList("e", "employee", "employees", "직원");
        private static final List<String> K_DIRECTOR_OFFER = Arrays.asList("d", "director", "directors", "이사");
        private static final List<String> K_REPRESENTATIVE_OFFER = Arrays.asList("r", "rep", "representative", "대표", "대표자");
        private static final List<String> K_MEMBERSHIP_OFFER = Arrays.asList("m", "member", "members", "membership", "회원", "회원권");

        @Nullable
        public static Type fromInput(@NonNull String input) {
            if (K_EMPLOYEE_OFFER.contains(input.toLowerCase())) return EMPLOYEE_OFFER;
            if (K_DIRECTOR_OFFER.contains(input.toLowerCase())) return DIRECTOR_OFFER;
            if (K_REPRESENTATIVE_OFFER.contains(input.toLowerCase())) return REPRESENTATIVE_OFFER;
            if (K_MEMBERSHIP_OFFER.contains(input.toLowerCase())) return MEMBERSHIP_OFFER;

            return null;
        }

        @NonNull
        public List<String> toInput() {
            return switch (this) {
                case EMPLOYEE_OFFER -> K_EMPLOYEE_OFFER;
                case DIRECTOR_OFFER -> K_DIRECTOR_OFFER;
                case REPRESENTATIVE_OFFER -> K_REPRESENTATIVE_OFFER;
                case MEMBERSHIP_OFFER -> K_MEMBERSHIP_OFFER;
                default -> new ArrayList<>();
            };
        }
    }

    final class Invitation implements Offer {
        private Invitation(@NonNull UUID uniqueId, @NonNull Actor sender, @NonNull Actor recipient, @NonNull Type type) {
            this.uniqueId = uniqueId;
            this.sender = sender;
            this.recipient = recipient;
            this.type = type;
        }

        @ConstructorProperties({"uniqueId", "sender", "recipient", "type"})
        private Invitation() {
            this.uniqueId = null;
            this.sender = null;
            this.recipient = null;
            this.type = null;
        }

        @NonNull
        @JsonProperty
        private final UUID uniqueId;

        @NonNull
        @JsonProperty
        @JsonIdentityReference
        private Actor sender;

        @NonNull
        @JsonProperty
        @JsonIdentityReference
        private Actor recipient;

        @NonNull
        @JsonProperty
        private final Type type;

        @NonNull
        @Override
        @JsonIgnore
        public UUID getUniqueId() {
            return uniqueId;
        }

        @NonNull
        @JsonIgnore
        @Override
        public Actor getSender() {
            return sender;
        }

        @NonNull
        @JsonIgnore
        @Override
        public Actor getRecipient() {
            return recipient;
        }

        @Override
        @JsonIgnore
        public Offer.@NonNull Type getType() {
            return type;
        }

        @Override
        public void onAccepted() throws RuntimeException {
            switch (type) {
                case EMPLOYEE_OFFER, DIRECTOR_OFFER -> {
                    if (sender instanceof Employer e && recipient instanceof Person p) {
                        switch (type) {
                            case EMPLOYEE_OFFER -> {
                                Bukkit.getPluginManager().callEvent(new EmployeeHiredEvent(p, e));
                                return;
                            }
                            case DIRECTOR_OFFER -> {
                                Bukkit.getPluginManager().callEvent(new DirectorHiredEvent(p, e));
                                return;
                            }
                        }
                    }

                    throw new RuntimeException();
                }

                case REPRESENTATIVE_OFFER -> {
                    if (sender instanceof Representable r && recipient instanceof Person p) {
                        Bukkit.getPluginManager().callEvent(new RepresentativeHiredEvent(p, r));
                        return;
                    }

                    throw new RuntimeException();
                }

                case MEMBERSHIP_OFFER -> {
                    switch (sender.getType()) {
                        case ALLIANCE -> {
                            if (sender instanceof Alliance a && recipient instanceof Sovereign s) {
                                Bukkit.getPluginManager().callEvent(new AllianceMemberAddedEvent(a, s));
                                return;
                            }
                        }
                        case CARTEL -> {
                            if (sender instanceof Cartel ct && recipient instanceof Corporation corp) {
                                Bukkit.getPluginManager().callEvent(new CartelMemberAddedEvent(ct, corp));
                                return;
                            }
                        }
                        case PARTY -> {
                            if (sender instanceof Party par && recipient instanceof Person per) {
                                Bukkit.getPluginManager().callEvent(new PartyMemberAddedEvent(par, per));
                                return;
                            }
                        }
                    }

                    throw new RuntimeException();
                }
            }
        }

        @Override
        public void onDeclined(@NonNull EconomyState state) {
            state.removeOffer(this);
        }

        @Override
        public void initialize(@NonNull EconomyState state) {
            for (Actor a : state.getActors()) {
                if (a.getUniqueId().equals(sender.getUniqueId())) {
                    sender = a;
                }

                if (a.getUniqueId().equals(recipient.getUniqueId())) {
                    recipient = a;
                }
            }
        }
    }
}
