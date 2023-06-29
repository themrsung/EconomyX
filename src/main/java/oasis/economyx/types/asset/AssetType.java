package oasis.economyx.types.asset;

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
