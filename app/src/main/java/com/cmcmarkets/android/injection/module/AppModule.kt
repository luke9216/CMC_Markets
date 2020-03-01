package com.cmcmarkets.android.injection.module

import android.content.Context
import com.cmcmarkets.android.CustomApp
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class AppModule {

    @Provides
    @Singleton
    fun provideContext(application: CustomApp): Context = application
}
