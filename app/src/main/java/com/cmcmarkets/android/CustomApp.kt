package com.cmcmarkets.android

import android.app.Activity
import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.cmcmarkets.android.injection.component.DaggerAppComponent
import com.cmcmarkets.api.internal.implementations.ConnectionMonitor
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import javax.inject.Inject

class CustomApp : Application(), HasActivityInjector {

    @Inject
    lateinit var activityInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> = activityInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent
                .builder()
                .application(this)
                .build()
                .inject(this)

        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            registerInternetChangesListener {
                ConnectionMonitor.isConnected = it
            }
        }
    }
}
