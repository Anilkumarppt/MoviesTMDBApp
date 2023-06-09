package com.impressico.moviesapp.presentation.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import androidx.annotation.RequiresApi
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject


class NetworkCheck @Inject constructor(@ApplicationContext private val context: Context) {
    @RequiresApi(Build.VERSION_CODES.M)
    fun isInternetAvailable(): Boolean {
        var result = false
        val connectivityManger =
            context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?
        connectivityManger?.let {
            it.getNetworkCapabilities(connectivityManger.activeNetwork)?.apply {
                result = when {
                    hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
                    hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
                    else -> false
                }
            }
        }
        return result
    }

}