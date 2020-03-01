package com.cmcmarkets.android.injection.module.activity

import com.cmcmarkets.android.exercise.composer.session.SessionActivity
import dagger.Module
import dagger.Provides


@Module
class SessionActivityModule {
    @Provides
    fun provideSessionActivity(sessionActivity: SessionActivity): SessionActivity = sessionActivity
}