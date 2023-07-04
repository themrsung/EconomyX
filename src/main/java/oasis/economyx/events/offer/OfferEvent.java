package oasis.economyx.events.offer;

import oasis.economyx.events.EconomyEvent;
import oasis.economyx.types.offer.Offer;
import org.checkerframework.checker.nullness.qual.NonNull;

public abstract class OfferEvent extends EconomyEvent {
    public OfferEvent(@NonNull Offer offer) {
        this.offer = offer;
    }

    @NonNull
    private final Offer offer;

    @NonNull
    public Offer getOffer() {
        return offer;
    }
}
