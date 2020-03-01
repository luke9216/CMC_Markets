package com.cmcmarkets.android.data.domain.repository

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.cmcmarkets.api.internal.implementations.MockSessionApi
import com.cmcmarkets.api.session.ISessionApi
import com.cmcmarkets.api.session.SessionTO
import io.reactivex.Single
import javax.inject.Inject

class SessionRepositoryImpl @Inject constructor(): SessionRepository {

    private var sessionRepository: SessionRepository? = null

    var iSessionApi: ISessionApi = MockSessionApi()

    @Synchronized
    override fun getInstance(): SessionRepository? {
        if (sessionRepository == null) {
            sessionRepository = this
        }
        return sessionRepository
    }

    override fun getSession(): SessionTO? = _userSession.value

    private val _userSession = MutableLiveData<SessionTO>()
    val userSession: LiveData<SessionTO> = _userSession

    override fun updateSession(session: SessionTO) = _userSession.postValue(session)

    override fun createSession(): Single<SessionTO> = iSessionApi.sessionTokenSingle()
}
