package oasis.economyx.types.message;

import com.fasterxml.jackson.annotation.*;
import oasis.economyx.interfaces.actor.Actor;
import oasis.economyx.interfaces.reference.References;
import oasis.economyx.state.EconomyState;
import org.checkerframework.checker.nullness.qual.NonNull;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.joda.time.DateTime;

import java.beans.ConstructorProperties;
import java.util.UUID;

/**
 * A message can be sent from actors to actors, or from the server to an actor (sender will be null)
 */
@JsonIdentityInfo(generator = ObjectIdGenerators.UUIDGenerator.class)
public final class Message implements References {
    public Message(
            @Nullable @JsonProperty @JsonIdentityReference Actor sender,
            @NonNull @JsonProperty @JsonIdentityReference Actor recipient,
            @NonNull @JsonProperty String content
    ) {
        this.uniqueId = UUID.randomUUID();
        this.sender = sender;
        this.recipient = recipient;
        this.content = content;
        this.time = new DateTime();
        this.shown = false;
    }

    @JsonProperty
    private final @NonNull UUID uniqueId;

    @JsonProperty
    @JsonIdentityReference
    private @Nullable Actor sender;

    @JsonProperty
    @JsonIdentityReference
    private @NonNull Actor recipient;

    @JsonProperty
    private final @NonNull String content;

    @JsonProperty
    private final @NonNull DateTime time;

    @JsonProperty
    private boolean shown;

    @NonNull
    @JsonIgnore
    public UUID getUniqueId() {
        return uniqueId;
    }

    @JsonIgnore
    public @Nullable Actor getSender() {
        return sender;
    }

    @JsonIgnore
    public @NonNull Actor getRecipient() {
        return recipient;
    }

    @JsonIgnore
    public @NonNull String getContent() {
        return content;
    }

    @JsonIgnore
    public @NonNull DateTime getTime() {
        return time;
    }

    @JsonIgnore
    public boolean isShown() {
        return shown;
    }

    @JsonIgnore
    public void setShown(boolean shown) {
        this.shown = shown;
    }

    @Override
    public void initialize(@NonNull EconomyState state) {
        for (Actor orig : state.getActors()) {
            if (sender != null) {
                if (orig.getUniqueId().equals(sender.getUniqueId())) {
                    sender = orig;
                }
            }

            if (orig.getUniqueId().equals(recipient.getUniqueId())) {
                recipient = orig;
            }
        }
    }

    @ConstructorProperties({"uniqueId", "sender", "recipient", "content", "time", "shown"})
    private Message() {
        this.uniqueId = null;
        this.sender = null;
        this.recipient = null;
        this.content = null;
        this.time = null;
        this.shown = false;
    }
}
