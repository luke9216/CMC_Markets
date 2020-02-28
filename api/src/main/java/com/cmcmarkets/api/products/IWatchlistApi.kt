package com.cmcmarkets.api.products

import io.reactivex.Observable
import io.reactivex.Single

/**
 * Watchlists Api will allow you to fetch and stream updates
 */
interface IWatchlistApi {
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

