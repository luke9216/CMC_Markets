package com.cmcmarkets.android.injection.module.activity

import com.cmcmarkets.android.exercise.composer.splash.SplashActivity
import dagger.Module
import dagger.Provides

@Module
class SplashActivityModule {
    @Provides
    fun provideSplashActivity(splashActivity: SplashActivity): SplashActivity = splashActivity
}