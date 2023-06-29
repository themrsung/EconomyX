package oasis.economyx.asset;

public enum AssetType {
    // Basic assets
    CASH,
    STOCK,
    COMMODITY,
    PROPERTY,

    /**
     * Used in casinos
     */
    CHIP,

    // Contracts
    FORWARD,
    NOTE,
    OPTION,
    SWAP,
    COLLATERAL;
}
