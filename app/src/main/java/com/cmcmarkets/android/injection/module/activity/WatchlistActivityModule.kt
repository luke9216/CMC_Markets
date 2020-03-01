package com.cmcmarkets.android.injection.module.activity

import com.cmcmarkets.android.exercise.composer.watchlist.WatchlistActivity
import com.cmcmarkets.android.exercise.composer.watchlist.WatchlistListFragment
import dagger.Module
import dagger.Provides


@Module
class WatchlistActivityModule {

    @Provides
    fun provideWatchlistActivity(watchlistActivity: WatchlistActivity): WatchlistActivity = watchlistActivity
}