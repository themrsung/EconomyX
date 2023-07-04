package oasis.economyx.events.offer;

import oasis.economyx.types.offer.Offer;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class OfferDeclinedEvent extends OfferEvent {
    public OfferDeclinedEvent(@NonNull Offer offer) {
        super(offer);
    }
}
