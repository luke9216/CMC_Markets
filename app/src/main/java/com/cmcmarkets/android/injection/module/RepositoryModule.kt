package com.cmcmarkets.android.injection.module

import com.cmcmarkets.android.data.domain.repository.*
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun sessionRepository(sessionRepositoryImpl: SessionRepositoryImpl): SessionRepository = sessionRepositoryImpl

    @Singleton
    @Provides
    fun productRepository(productRepositoryImpl: ProductRepositoryImpl): ProductRepository = productRepositoryImpl

    @Singleton
    @Provides
    fun watchlistRepository(watchlistRepositoryImpl: WatchlistRepositoryImpl): WatchlistRepository = watchlistRepositoryImpl
}
