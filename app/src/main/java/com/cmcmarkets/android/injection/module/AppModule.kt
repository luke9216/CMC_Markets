package com.cmcmarkets.android.injection.module

import android.content.Context
import com.cmcmarkets.android.CustomApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Singleton
    @Provides
    fun provideContext(application: CustomApp): Context = application
}
