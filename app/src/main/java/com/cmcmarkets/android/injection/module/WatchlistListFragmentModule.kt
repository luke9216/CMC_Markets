package com.cmcmarkets.android.injection.module

import com.cmcmarkets.android.exercise.composer.watchlist.WatchlistListFragment
import dagger.Module
import dagger.Provides


@Module
class WatchlistListFragmentModule {

    @Provides
    fun provideWatchlistListFragment(watchlistListFragment: WatchlistListFragment): WatchlistListFragment = watchlistListFragment
}