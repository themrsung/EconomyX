package oasis.economyx.types.asset;

import com.fasterxml.jackson.annotation.JsonSubTypes;
import com.fasterxml.jackson.annotation.JsonTypeInfo;
import oasis.economyx.types.asset.cash.CashMeta;
import oasis.economyx.types.asset.commodity.CommodityMeta;
import oasis.economyx.types.asset.contract.collateral.CollateralMeta;
import oasis.economyx.types.asset.contract.forward.ForwardMeta;
import oasis.economyx.types.asset.contract.note.NoteMeta;
import oasis.economyx.types.asset.contract.option.OptionMeta;
import oasis.economyx.types.asset.contract.swap.SwapMeta;
import oasis.economyx.types.asset.stock.StockMeta;

/**
 * Metadata of an asset
 * Mutable
 */
@JsonTypeInfo(
        use = JsonTypeInfo.Id.NAME,
        property = "type"
)

@JsonSubTypes({
        @JsonSubTypes.Type(value = CashMeta.class, name = "cash_meta"),
        @JsonSubTypes.Type(value = CommodityMeta.class, name = "commodity_meta"),
        @JsonSubTypes.Type(value = StockMeta.class, name = "stock_meta"),
        @JsonSubTypes.Type(value = CollateralMeta.class, name = "collateral_meta"),
        @JsonSubTypes.Type(value = ForwardMeta.class, name = "forward_meta"),
        @JsonSubTypes.Type(value = NoteMeta.class, name = "note_meta"),
        @JsonSubTypes.Type(value = OptionMeta.class, name = "option_meta"),
        @JsonSubTypes.Type(value = SwapMeta.class, name = "swap_meta")
})

public interface AssetMeta {

}
