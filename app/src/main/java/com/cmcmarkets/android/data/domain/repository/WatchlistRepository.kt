package com.cmcmarkets.android.data.domain.repository

import com.cmcmarkets.api.products.WatchlistTO
import com.cmcmarkets.api.products.WatchlistUpdateTO
import io.reactivex.Observable
import io.reactivex.Single

interface WatchlistRepository {

    /**
     * The latest watchlist list information. Subscribing to this observable will ensure you get the
     * latest watchlist list
     *
     * @param sessionToken - the current session token from {@link com.cmcmarkets.api.session.ISessionApi}
     *
     * @return A stream of most up to date watchlist or {@link com.cmcmarkets.api.ApiError}
     */
    fun watchlistsSingle(sessionToken: String): Single<List<WatchlistTO>>

    /**
     * The updates to specific watchlists. This will get the updates to the watchlist from the moment
     * of the subscription and will not be replayed of past updates.
     *
     * @param sessionToken - the current session token from {@link com.cmcmarkets.api.session.ISessionApi}
     *
     * @return A stream of updates to Watchlist or {@link com.cmcmarkets.api.ApiError}. This stream never completes
     */
    fun watchlistUpdatesObservable(sessionToken: String): Observable<WatchlistUpdateTO>
}