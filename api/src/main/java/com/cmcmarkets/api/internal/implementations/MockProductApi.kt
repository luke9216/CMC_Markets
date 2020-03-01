package com.cmcmarkets.api.internal.implementations

import com.cmcmarkets.api.ApiError
import com.cmcmarkets.api.products.IProductApi
import com.cmcmarkets.api.products.PriceTO
import com.cmcmarkets.api.products.ProductDetailsTO
import com.cmcmarkets.api.products.ProductTO
import io.reactivex.Flowable
import io.reactivex.Observable
import io.reactivex.Single
import java.math.BigDecimal
import java.math.MathContext
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

/**
 * DO NOT MODIFY. Please deliver the solution without tweaking this file
 *
 * Mock implementation of Product API
 */
@Singleton
class MockProductApi
@Inject
constructor() : IProductApi {
    private var prices = generatePrices()

    init {
        Observable.interval(500, TimeUnit.MILLISECONDS)
                .doOnNext { prices = generatePrices() }
                .subscribe()
    }

    override fun productSingle(sessionToken: String, id: Long): Single<ProductTO> =
            verifyConnection().andThen(verifySession(sessionToken))
                    .andThen(networkDelay())
                    .andThen(PRODUCT_TOS[id]?.wrapSingleOr { productDoesNotExistError(id) })

    override fun productDetailsSingle(sessionToken: String, id: Long): Single<ProductDetailsTO> =
            verifyConnection().andThen(verifySession(sessionToken))
                    .andThen(networkDelay())
                    .andThen(PRODUCT_DETAILS_TOS[id]?.wrapSingleOr { productDoesNotExistError(id) })

    override fun productPrices(sessionToken: String, id: Long): Flowable<PriceTO> =
            verifyConnection().andThen(verifySession(sessionToken))
                    .andThen(networkDelay())
                    .andThen(Single.defer { prices[id]?.wrapSingleOr { productDoesNotExistError(id) } })
                    .flatMap(occasionallyFail())
                    .repeat()

    private fun generatePrices() = PRODUCT_IDS.map {
        it to PriceTO(
                buy = BigDecimal(RANDOM.nextDouble(), MathContext.DECIMAL32),
                sell = BigDecimal(RANDOM.nextDouble(), MathContext.DECIMAL32)
        )
    }.toMap()

    private fun <T> T?.wrapSingleOr(alternative: () -> Single<T>) =
            if (this != null) Single.just(this)
            else alternative()

    private fun <T> productDoesNotExistError(id: Long) =
            Single.error<T>(ApiError.Other(NoSuchElementException("Product $id does not exist")))
}