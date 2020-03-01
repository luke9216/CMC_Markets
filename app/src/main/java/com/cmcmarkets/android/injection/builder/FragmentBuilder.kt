package com.cmcmarkets.android.injection.builder

import com.cmcmarkets.android.exercise.composer.watchlist.WatchlistListFragment
import com.cmcmarkets.android.injection.annotation.FragmentScope
import com.cmcmarkets.android.injection.module.RepositoryModule
import com.cmcmarkets.android.injection.module.WatchlistListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class FragmentBuilder {

    @FragmentScope
    @ContributesAndroidInjector(modules = [WatchlistListFragmentModule::class])
    abstract fun provideWatchlistListFragment() : WatchlistListFragment
}