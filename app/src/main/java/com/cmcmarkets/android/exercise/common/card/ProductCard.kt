package com.cmcmarkets.android.exercise.common.card

import android.content.Context
import android.text.Editable
import android.text.TextWatcher
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.cmcmarkets.android.data.gateway.entity.ProductDTO
import com.cmcmarkets.android.exercise.R
import com.cmcmarkets.api.products.PriceTO
import io.reactivex.BackpressureStrategy
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers


class ProductCard(context: Context) : ConstraintLayout(context) {
    init {
        layoutParams = LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT)
        View.inflate(context, R.layout.card_product, this)
    }

    /**
     * Product View Card
     *
     * @param productDTO - Product, ProductDetails and Price as ArrayList
     */
    fun setProduct(productDTO: ProductDTO) {
        findViewById<TextView>(R.id.name).text = productDTO.productTO?.name
        findViewById<TextView>(R.id.type).text = productDTO.productTO?.type

        val txtSell = findViewById<TextView>(R.id.sell_price)
        val txtBuy = findViewById<TextView>(R.id.buy_price)
        if(productDTO.priceTO?.sell == null || productDTO.priceTO?.buy == null)
        {
            txtSell.text = "-"
            txtBuy.text = "-"
        } else {
            val res = resources
            val lblSell = String.format(res.getString(R.string.productPrice, productDTO.priceTO?.sell))
            txtSell.text = lblSell
            val lblBuy = String.format(res.getString(R.string.productPrice, productDTO.priceTO?.buy))
            txtBuy.text = lblBuy
        }

        findViewById<TextView>(R.id.country_currency).text = productDTO.productDetailsTO?.currency
        findViewById<TextView>(R.id.spread).text = productDTO.productDetailsTO?.spread.toString()
        findViewById<TextView>(R.id.margin).text = productDTO.productDetailsTO?.margin.toString()
    }
}