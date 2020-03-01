package com.cmcmarkets.android

import android.net.ConnectivityManager
import android.net.Network
import android.os.Build

fun ConnectivityManager.registerInternetChangesListener(
        onChanged: (isConnected: Boolean) -> Unit
) = if (Build.VERSION.SDK_INT < Build.VERSION_CODES.N) {
    onChanged(activeNetworkInfo?.isConnectedOrConnecting ?: false)
    addDefaultNetworkActiveListener {
        onChanged(activeNetworkInfo?.isConnectedOrConnecting ?: false)
    }
} else {
    registerDefaultNetworkCallback(object : ConnectivityManager.NetworkCallback() {
        override fun onAvailable(network: Network) {
            onChanged(true)
        }

        override fun onLosing(network: Network, maxMsToLive: Int) {
            onChanged(false)
        }

        override fun onLost(network: Network) {
            onChanged(false)
        }
    })
}


