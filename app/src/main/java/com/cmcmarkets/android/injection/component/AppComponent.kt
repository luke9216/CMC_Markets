package com.cmcmarkets.android.injection.component

import android.content.Context
import com.cmcmarkets.android.CustomApp
import com.cmcmarkets.android.injection.module.ActivityModule
import com.cmcmarkets.android.injection.module.ActivityViewModule
import com.cmcmarkets.android.injection.module.AppModule
import com.cmcmarkets.android.injection.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules= [AndroidInjectionModule::class, AppModule::class, ActivityModule::class, ActivityViewModule::class, RepositoryModule::class])
interface AppComponent: AndroidInjector<CustomApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(context: Context): Builder

        fun build(): AppComponent
    }

    override fun inject(instance: CustomApp)
}