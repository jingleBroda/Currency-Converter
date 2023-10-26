package com.example.presentation.activity_contract

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.fragment.app.Fragment

interface InternetConnection{
    fun isInternetConnected():Boolean
}

fun Fragment.internetConnection() = requireActivity() as InternetConnection

class NetworkConnectivityChecker(private val context: Context) {
    private var connectivityManager: ConnectivityManager =
        context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
    private var networkCallback = object : ConnectivityManager.NetworkCallback() {
        // Сеть доступна
        override fun onAvailable(network: Network) {}
        // Сеть недоступна
        override fun onLost(network: Network) {}
    }

    fun startListening() {
        val networkRequest = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .build()
        connectivityManager.registerNetworkCallback(networkRequest, networkCallback)
    }

    fun stopListening() {
        connectivityManager.unregisterNetworkCallback(networkCallback)
    }

    fun isInternetConnected(): Boolean {
        val network = connectivityManager.activeNetwork
        val capabilities = connectivityManager.getNetworkCapabilities(network)
        return capabilities?.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET) == true
    }
}