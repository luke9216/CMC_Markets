package com.cmcmarkets.api.products

data class ProductDetailsTO(
        val country: String,
        val type: String,
        val subtype: String,
        val currency: String,
        val spread: Double,
        val margin: Int
)
