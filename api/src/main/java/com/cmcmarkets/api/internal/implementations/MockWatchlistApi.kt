package com.cmcmarkets.api.internal.implementations

import com.cmcmarkets.api.products.*
import io.reactivex.Observable
import io.reactivex.Single
import io.reactivex.subjects.PublishSubject
import java.util.*
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton


/**
 * DO NOT MODIFY. Please deliver the solution without tweaking this file
 *
 * Mock implementation of Watchlist API
 */
@Singleton
class MockWatchlistApi
@Inject
constructor() : IWatchlistApi {
    private var watchlists = (0 until 10).map { createNewWatchlist() }
    private val updatesSubject = PublishSubject.create<WatchlistUpdateTO>()

    init {
        Observable.interval(0L, 15L, TimeUnit.SECONDS)
                .doOnNext {
                    val operation = WatchlistOperationType.values().random(RANDOM)
                    val updateTO: WatchlistUpdateTO
                    synchronized(this) {
                        when (operation) {
                            WatchlistOperationType.ADD_OR_UPDATE -> {
                                val newWatchlist: WatchlistTO
                                if (watchlists.isNotEmpty() && RANDOM.nextBoolean()) {
                                    val watchlistIndex = watchlists.indices.random(RANDOM)
                                    val watchlistToUpdate = watchlists[watchlistIndex]
                                    newWatchlist = watchlistToUpdate.copy(details = watchlistToUpdate.details.copy(productIds = createProductList()))
                                    watchlists = watchlists.toMutableList().apply { this[watchlistIndex] = newWatchlist }
                                } else {
                                    newWatchlist = createNewWatchlist()
                                    watchlists += newWatchlist
                                }
                                updateTO = WatchlistUpdateTO(newWatchlist.id, WatchlistOperationType.ADD_OR_UPDATE, newWatchlist.details)
                            }
                            WatchlistOperationType.DELETE -> {
                                if (watchlists.isEmpty()) return@doOnNext
                                val watchlistToDelete = watchlists.random(RANDOM)
                                watchlists -= watchlistToDelete
                                updateTO = WatchlistUpdateTO(watchlistToDelete.id, WatchlistOperationType.DELETE, null)
                            }
                        }
                    }

                    updatesSubject.onNext(updateTO)
                }
                .subscribe()
    }

    override fun watchlistsSingle(sessionToken: String) =
            verifyConnection().andThen(verifySession(sessionToken)).andThen(networkDelay())
                    .andThen(Single.fromCallable { watchlists }.flatMap(occasionallyFail()))

    override fun watchlistUpdatesObservable(sessionToken: String) =
            verifyConnection().andThen(verifySession(sessionToken))
                    .andThen(networkDelay())
                    .andThen(updatesSubject.flatMapSingle(occasionallyFail()))

    private fun createNewWatchlist(): WatchlistTO {
        val newDetails = WatchlistDetailsTO(
                name = "Watchlist" + RANDOM.nextLong(1000),
                productIds = createProductList()
        )
        val newId = UUID.randomUUID().toString()
        return WatchlistTO(newId, newDetails)
    }

    private fun createProductList(): List<Long> = PRODUCT_IDS.shuffled(RANDOM).subList(0, RANDOM.nextInt(PRODUCT_IDS.size))
}
