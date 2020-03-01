package com.cmcmarkets.api.internal.implementations

import com.cmcmarkets.api.ApiError
import io.reactivex.Completable

/**
 * DO NOT MODIFY. Please deliver the solution without tweaking this file
 */

internal var CURRENT_SESSION_TOKEN: String? = null

internal fun verifySession(sessionToken: String) = Completable.defer {
    if (sessionToken == CURRENT_SESSION_TOKEN) Completable.complete()
    else Completable.error(ApiError.SessionUnrecognized)
}
