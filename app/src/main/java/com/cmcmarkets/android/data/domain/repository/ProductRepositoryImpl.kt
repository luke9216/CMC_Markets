package com.cmcmarkets.android.data.domain.repository

import com.cmcmarkets.api.internal.implementations.MockProductApi
import com.cmcmarkets.api.products.IProductApi
import com.cmcmarkets.api.products.PriceTO
import com.cmcmarkets.api.products.ProductDetailsTO
import com.cmcmarkets.api.products.ProductTO
import io.reactivex.Flowable
import io.reactivex.Single
import javax.inject.Inject

class ProductRepositoryImpl @Inject constructor(): ProductRepository {

    var productApi: IProductApi = MockProductApi()

    override fun productSingle(sessionToken: String, id: Long): Single<ProductTO> = productApi.productSingle(sessionToken, id)

    override fun productDetailsSingle(sessionToken: String, id: Long): Single<ProductDetailsTO> = productApi.productDetailsSingle(sessionToken, id)

    override fun productPrices(sessionToken: String, id: Long): Flowable<PriceTO> = productApi.productPrices(sessionToken, id)

}