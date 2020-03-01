package com.cmcmarkets.android.data.domain.repository

import com.cmcmarkets.api.internal.implementations.MockWatchlistApi
import com.cmcmarkets.api.products.IWatchlistApi
import com.cmcmarkets.api.products.WatchlistTO
import com.cmcmarkets.api.products.WatchlistUpdateTO
import io.reactivex.Observable
import io.reactivex.Single
import javax.inject.Inject

class WatchlistRepositoryImpl @Inject constructor(): WatchlistRepository {

    private val watchlistApi: IWatchlistApi = MockWatchlistApi()

    override fun watchlistsSingle(sessionToken: String): Single<List<WatchlistTO>> = watchlistApi.watchlistsSingle(sessionToken)

    override fun watchlistUpdatesObservable(sessionToken: String): Observable<WatchlistUpdateTO> = watchlistApi.watchlistUpdatesObservable(sessionToken)
}