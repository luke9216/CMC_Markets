package com.cmcmarkets.android.data.domain.repository

import com.cmcmarkets.api.session.SessionTO
import io.reactivex.Single

interface SessionRepository {

    fun getInstance(): SessionRepository?

    fun getSession(): SessionTO?

    fun createSession(): Single<SessionTO>

    fun updateSession(session: SessionTO)
}