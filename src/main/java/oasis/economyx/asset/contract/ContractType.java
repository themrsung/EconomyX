package oasis.economyx.asset.contract;

public enum ContractType {
    /**
     * A unilateral contract which grants the holder the right to receive an asset on expiry
     */
    NOTE,

    /**
     * A bilateral binding contract between two parties to exchange an asset for cash on expiry
     */
    FORWARD,

    /**
     * An asymmetrical contract between two parties which give the holder the right,
     * but not the obligation to either buy or sell an asset for cash on expiry
     */
    OPTION,

    /**
     * A bilateral binding contract between two parties to exchange the cash flow of two assets on expiry
     * Settled in cash
     */
    SWAP
}
