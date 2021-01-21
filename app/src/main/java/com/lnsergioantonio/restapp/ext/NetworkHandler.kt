package com.lnsergioantonio.restapp.ext

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build

/**
 * Is network connected
 */
fun Context?.isNetworkConnected(): Boolean {
    var isConnected = false
    this?.let {
        val connectivityManager = (getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager?)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            connectivityManager?.run {
                connectivityManager.getNetworkCapabilities(connectivityManager.activeNetwork)?.run {
                    isConnected = hasTransport(NetworkCapabilities.TRANSPORT_WIFI) || hasTransport(
                        NetworkCapabilities.TRANSPORT_CELLULAR)
                }
            }
        } else {
            connectivityManager?.run {
                connectivityManager.activeNetworkInfo?.run {
                    isConnected = (type == ConnectivityManager.TYPE_WIFI) || (type == ConnectivityManager.TYPE_MOBILE)
                }
            }
        }
    }
    return isConnected
}

class NetworkHandler constructor(private val context: Context) {
    val isConnected get() = context.isNetworkConnected()
}
