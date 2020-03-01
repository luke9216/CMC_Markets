package com.cmcmarkets.android.injection.component

import android.app.Application
import com.cmcmarkets.android.CustomApp
import com.cmcmarkets.android.injection.builder.ActivityBuilder
import com.cmcmarkets.android.injection.builder.FragmentBuilder
import com.cmcmarkets.android.injection.module.AppModule
import com.cmcmarkets.android.injection.module.RepositoryModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import dagger.android.AndroidInjector
import javax.inject.Singleton

@Singleton
@Component(modules = [AndroidInjectionModule::class, AppModule::class, RepositoryModule::class, ActivityBuilder::class, FragmentBuilder::class])
interface AppComponent : AndroidInjector<CustomApp> {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(app: Application): Builder
//        fun fragment(fragment: Fragment): Builder
//        fun repositoryModule(repositoryModule: RepositoryModule): Builder
//        fun appModule(appModule: AppModule): Builder
        fun build(): AppComponent
    }

//    override fun inject(instance: CustomApp)
}