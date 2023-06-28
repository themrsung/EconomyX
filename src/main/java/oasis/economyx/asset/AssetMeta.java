package oasis.economyx.asset;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.asset.cash.CashMeta;
import oasis.economyx.asset.commodity.CommodityMeta;
import oasis.economyx.asset.contract.collateral.CollateralMeta;
import oasis.economyx.asset.contract.forward.ForwardMeta;
import oasis.economyx.asset.contract.note.NoteMeta;
import oasis.economyx.asset.contract.option.OptionMeta;
import oasis.economyx.asset.contract.swap.SwapMeta;

/**
 * Metadata of an asset
 * Mutable
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = CashMeta.class, name = "cashMeta"),
        @JsonSubTypes.Type(value = CommodityMeta.class, name = "commodityMeta"),
        @JsonSubTypes.Type(value = CollateralMeta.class, name = "collateralMeta"),
        @JsonSubTypes.Type(value = ForwardMeta.class, name = "forwardMeta"),
        @JsonSubTypes.Type(value = NoteMeta.class, name = "noteMeta"),
        @JsonSubTypes.Type(value = OptionMeta.class, name = "optionMeta"),
        @JsonSubTypes.Type(value = SwapMeta.class, name = "swapMeta")
})

public interface AssetMeta {

}
