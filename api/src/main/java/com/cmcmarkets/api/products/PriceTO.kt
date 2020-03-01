package com.cmcmarkets.api.products

import java.math.BigDecimal

data class PriceTO(
        val buy: BigDecimal?,
        val sell: BigDecimal?
)