package oasis.economyx.events.offer;

import oasis.economyx.types.offer.Offer;
import org.checkerframework.checker.nullness.qual.NonNull;

public final class OfferAcceptedEvent extends OfferEvent {
    public OfferAcceptedEvent(@NonNull Offer offer) {
        super(offer);
    }
}
