package com.cmcmarkets.api.internal.implementations

import com.cmcmarkets.api.session.ISessionApi
import com.cmcmarkets.api.session.SessionTO
import io.reactivex.Single
import java.util.*
import javax.inject.Inject
import javax.inject.Singleton

/**
 * DO NOT MODIFY. Please deliver the solution without tweaking this file
 *
 * Mock implementation of Session API
 */
@Singleton
class MockSessionApi
@Inject
constructor() : ISessionApi {
    override fun sessionTokenSingle(): Single<SessionTO> =
            verifyConnection()
                    .andThen(networkDelay())
                    .andThen(Single.fromCallable { generateSessionResponse() })

    private fun generateSessionResponse(): SessionTO {
        val sessionId = generateSession()
        return SessionTO(sessionId != null, sessionId ?: "")
    }

    private fun generateSession(): String? {
        if (RANDOM.nextBoolean()) {
            val sessionId = UUID.randomUUID().toString()
            CURRENT_SESSION_TOKEN = sessionId
            return sessionId
        } else {
            return null
        }
    }
}
