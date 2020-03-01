package com.cmcmarkets.api.internal.implementations

import com.cmcmarkets.api.ApiError
import io.reactivex.Completable

/**
 * DO NOT MODIFY. Please deliver the solution without tweaking this file
 */
object ConnectionMonitor {
    /**
     * DO NOT WRITE TO THIS VARIABLE
     */
    var isConnected = false
}

internal fun verifyConnection() = Completable.defer {
    if (ConnectionMonitor.isConnected) Completable.complete()
    else Completable.error(ApiError.Disconnected)
}