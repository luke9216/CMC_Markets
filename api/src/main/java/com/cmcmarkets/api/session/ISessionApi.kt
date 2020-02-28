package com.cmcmarkets.api.session

import io.reactivex.Single

/**
 * Session API to generate session tokens to make subsequent Api requests
 */
interface ISessionApi {
    /**
     * Generate a session token with the server
     *
     * @return A success paired with a token or a failure. This stream does not have errors for simplicity
     */
    fun sessionTokenSingle(): Single<SessionTO>
}

