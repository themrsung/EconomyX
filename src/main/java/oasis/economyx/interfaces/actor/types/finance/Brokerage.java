package oasis.economyx.interfaces.actor.types.finance;

import org.checkerframework.checker.index.qual.NonNegative;

/**
 * A brokerage can hold assets and place orders on their clients' behalf
 * Deposited assets can be utilized by the brokerage for any purpose
 * Unlike a bank, brokerages cannot pay interest on deposits
 */
public interface Brokerage extends Banker {
    /**
     * Gets the brokerage fee rate
     *
     * @return Rate (e.g. 2.3% -> 0.023f)
     */
    @NonNegative
    float getBrokerageFeeRate();

    /**
     * Sets the brokerage fee rate
     *
     * @param rate Rate (e.g. 2.3% -> 0.023f)
     */
    void setBrokerageFeeRate(@NonNegative float rate);

}
