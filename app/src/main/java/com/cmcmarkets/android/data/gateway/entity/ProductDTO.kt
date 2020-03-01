package com.cmcmarkets.android.data.gateway.entity

import com.cmcmarkets.api.products.PriceTO
import com.cmcmarkets.api.products.ProductDetailsTO
import com.cmcmarkets.api.products.ProductTO
import com.google.gson.annotations.SerializedName

data class ProductDTO (

        @SerializedName("productId")
        var productId: Long,

        @SerializedName("product")
        var productTO: ProductTO?,

        @SerializedName("productDetails")
        var productDetailsTO: ProductDetailsTO?
)