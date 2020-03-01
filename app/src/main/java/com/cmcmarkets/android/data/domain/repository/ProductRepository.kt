package com.cmcmarkets.android.data.domain.repository

import com.cmcmarkets.api.products.PriceTO
import com.cmcmarkets.api.products.ProductDetailsTO
import com.cmcmarkets.api.products.ProductTO
import io.reactivex.Flowable
import io.reactivex.Single

interface ProductRepository {

    fun productSingle(sessionToken: String, id: Long): Single<ProductTO>

    fun productDetailsSingle(sessionToken: String, id: Long): Single<ProductDetailsTO>

    fun productPrices(sessionToken: String, id: Long): Flowable<PriceTO>
}