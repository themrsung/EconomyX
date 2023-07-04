package oasis.economyx.events.offer;

import oasis.economyx.types.offer.Offer;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class OfferSentEvent extends OfferEvent {
    public OfferSentEvent(@NonNull Offer offer) {
        super(offer);
    }
}
