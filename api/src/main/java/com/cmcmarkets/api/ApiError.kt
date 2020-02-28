package com.cmcmarkets.api

/**
 * Possible errors that can be returned by any Api call
 */
sealed class ApiError(cause: Throwable? = null) : Exception(cause) {
    /**
     * Error when session token has expired and needs to be re-generated.
     * See {@link com.cmcmarkeets.api.internal.FailedRates} for rates of error
     */
    object SessionExpired : ApiError()

    /**
     * Error when session token does not match the current session token
     */
    object SessionUnrecognized : ApiError()

    /**
     * Error when device is disconnect.
     * See {@link com.cmcmarkeets.api.internal.FailedRates} for rates of error
     *
     * NOTE: this does not occur in Airplane mode with the Mock APIs
     */
    object Disconnected : ApiError()

    /**
     * Unexpected errors.
     */
    class Other(cause: Throwable?) : ApiError(cause)
}
