package com.cmcmarkets.api.internal.implementations

import com.cmcmarkets.api.ApiError
import com.cmcmarkets.api.internal.EXPIRY_RATE
import io.reactivex.Completable
import io.reactivex.Single
import java.util.*
import java.util.concurrent.TimeUnit

/**
 * DO NOT MODIFY. Please deliver the solution without tweaking this file
 */

internal fun <T> occasionallyFail(): (T) -> Single<T> = {
    if (RANDOM.nextDouble() < EXPIRY_RATE) {
        CURRENT_SESSION_TOKEN = UUID.randomUUID().toString()
        Single.error(ApiError.SessionExpired)
    } else Single.just(it)
}


internal fun networkDelay() = Completable.timer(RANDOM.nextLong(1000L), TimeUnit.MILLISECONDS)