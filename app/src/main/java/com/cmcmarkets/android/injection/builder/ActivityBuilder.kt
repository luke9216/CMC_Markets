package com.cmcmarkets.android.injection.builder

import com.cmcmarkets.android.exercise.base.BaseActivity
import com.cmcmarkets.android.exercise.composer.watchlist.WatchlistActivity
import com.cmcmarkets.android.injection.annotation.ActivityScope
import com.cmcmarkets.android.injection.module.BaseActivityModule
import com.cmcmarkets.android.injection.module.RepositoryModule
import com.cmcmarkets.android.injection.module.WatchlistActivityModule
import com.cmcmarkets.android.injection.module.WatchlistListFragmentModule
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class ActivityBuilder {

    @ActivityScope
    @ContributesAndroidInjector(modules = [BaseActivityModule::class])
    abstract fun contributeBaseActivity(): BaseActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [WatchlistActivityModule::class, WatchlistListFragmentModule::class])
    abstract fun contributeWatchlistActivity(): WatchlistActivity

}