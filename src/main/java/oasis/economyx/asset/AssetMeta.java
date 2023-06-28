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
import oasis.economyx.asset.stock.StockMeta;

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
