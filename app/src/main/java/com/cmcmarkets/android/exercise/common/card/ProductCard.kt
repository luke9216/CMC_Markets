package com.cmcmarkets.android.exercise.common.card

import android.content.Context
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.cmcmarkets.android.data.gateway.entity.ProductDTO
import com.cmcmarkets.android.exercise.R

class ProductCard(context: Context) : ConstraintLayout(context) {
    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.card_product, this)
    }

    /**
     * Product View Card
     *
     * @param productDTO
     */
    fun setProduct(productDTO: ProductDTO) {
        findViewById<TextView>(R.id.name).text = productDTO.productTO.name
        findViewById<TextView>(R.id.type).text = productDTO.productTO.type

        findViewById<TextView>(R.id.sell_price).text = productDTO.priceTO.sell.toString()
        findViewById<TextView>(R.id.buy_price).text = productDTO.priceTO.buy.toString()

        findViewById<TextView>(R.id.country_currency).text = productDTO.productDetailsTO.currency
        findViewById<TextView>(R.id.spread).text = productDTO.productDetailsTO.spread.toString()
        findViewById<TextView>(R.id.margin).text = productDTO.productDetailsTO.margin.toString()
    }
}