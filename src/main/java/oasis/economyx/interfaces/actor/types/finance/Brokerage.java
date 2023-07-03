package oasis.economyx.interfaces.actor.types.finance;

import com.fasterxml.jackson.annotation.JsonIgnore;
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
    @JsonIgnore
    float getBrokerageFeeRate();

    /**
     * Sets the brokerage fee rate
     *
     * @param rate Rate (e.g. 2.3% -> 0.023f)
     */
    @JsonIgnore
    void setBrokerageFeeRate(@NonNegative float rate);

}
