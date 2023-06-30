package oasis.economyx.interfaces.card;

import oasis.economyx.interfaces.actor.types.services.CardAcceptor;
import oasis.economyx.types.asset.cash.CashStack;

public interface CardTerminal {
    CardAcceptor getSeller();
    CashStack getPrice();
}
