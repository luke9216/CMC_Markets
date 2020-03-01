package com.cmcmarkets.api.products

import io.reactivex.Flowable
import io.reactivex.Single

/**
 * Products Api will allow you to get information of a product based on its ID.
 */
interface IProductApi {
    /**
     * Use this to get basic information of a product - name and type
     *
     * @param sessionToken - the current session token from {@link com.cmcmarkets.api.session.ISessionApi}
     * @param id - product id
     *
     * @return A stream of one product or {@link com.cmcmarkets.api.ApiError}
     */
    fun productSingle(sessionToken: String, id: Long): Single<ProductTO>

    /**
     * Use this to get detailed information of a product such as its country and currency
     *
     * @param sessionToken - the current session token from {@link com.cmcmarkets.api.session.ISessionApi}
     * @param id - product id
     *
     * @return A stream of one product details or {@link com.cmcmarkets.api.ApiError}
     */
    fun productDetailsSingle(sessionToken: String, id: Long): Single<ProductDetailsTO>


    /**
     * Use this to stream product prices - buy and sell
     *
     * @param sessionToken - the current session token from {@link com.cmcmarkets.api.session.ISessionApi}
     * @param id - product id
     *
     * @return A stream of product prices which may be terminated by an
     * {@link com.cmcmarkets.api.ApiError} at any time. This stream never completes
     */
    fun productPrices(sessionToken: String, id: Long): Flowable<PriceTO>
}

