package com.cmcmarkets.android

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import com.cmcmarkets.api.internal.implementations.ConnectionMonitor

class CustomApp : Application() {
    override fun onCreate() {
        super.onCreate()
        (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager).apply {
            registerInternetChangesListener {
                ConnectionMonitor.isConnected = it
            }
        }
    }
}
