package com.cmcmarkets.android.injection.builder

import com.cmcmarkets.android.exercise.base.BaseActivity
import com.cmcmarkets.android.exercise.composer.session.SessionActivity
import com.cmcmarkets.android.exercise.composer.splash.SplashActivity
import com.cmcmarkets.android.exercise.composer.watchlist.WatchlistActivity
import com.cmcmarkets.android.injection.annotation.ActivityScope
import com.cmcmarkets.android.injection.module.BaseActivityModule
import com.cmcmarkets.android.injection.module.activity.WatchlistActivityModule
import com.cmcmarkets.android.injection.module.WatchlistListFragmentModule
import com.cmcmarkets.android.injection.module.activity.SessionActivityModule
import com.cmcmarkets.android.injection.module.activity.SplashActivityModule
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

    @ActivityScope
    @ContributesAndroidInjector(modules = [SplashActivityModule::class])
    abstract fun contributeSplashActivity(): SplashActivity

    @ActivityScope
    @ContributesAndroidInjector(modules = [SessionActivityModule::class])
    abstract fun contributeSessionActivity(): SessionActivity
}