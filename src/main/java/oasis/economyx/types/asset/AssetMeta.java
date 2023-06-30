package oasis.economyx.types.asset;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.types.asset.cash.CashMeta;
import oasis.economyx.types.asset.chip.ChipMeta;
import oasis.economyx.types.asset.commodity.CommodityMeta;
import oasis.economyx.types.asset.contract.collateral.CollateralMeta;
import oasis.economyx.types.asset.contract.forward.ForwardMeta;
import oasis.economyx.types.asset.contract.note.NoteMeta;
import oasis.economyx.types.asset.contract.option.OptionMeta;
import oasis.economyx.types.asset.contract.swap.SwapMeta;
import oasis.economyx.types.asset.property.PropertyMeta;
import oasis.economyx.types.asset.stock.StockMeta;
import org.checkerframework.checker.nullness.qual.NonNull;

/**
 * Metadata of an asset
 * Mutable
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = CashMeta.class, name = "CASH"),
        @JsonSubTypes.Type(value = CommodityMeta.class, name = "COMMODITY"),
        @JsonSubTypes.Type(value = StockMeta.class, name = "STOCK"),
        @JsonSubTypes.Type(value = PropertyMeta.class, name = "PROPERTY"),
        @JsonSubTypes.Type(value = ChipMeta.class, name = "CHIP"),
        @JsonSubTypes.Type(value = CollateralMeta.class, name = "COLLATERAL"),
        @JsonSubTypes.Type(value = ForwardMeta.class, name = "FORWARD"),
        @JsonSubTypes.Type(value = NoteMeta.class, name = "NOTE"),
        @JsonSubTypes.Type(value = OptionMeta.class, name = "OPTION"),
        @JsonSubTypes.Type(value = SwapMeta.class, name = "SWAP")
})

public interface AssetMeta {
    Asset.Type getType();
}
