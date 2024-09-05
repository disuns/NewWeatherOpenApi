package com.project.newweatheropenapi.utils

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import com.orhanobut.logger.Logger

fun Context.isNetworkCheck(): Boolean {
    val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager ?: return false
    val nw = cm.activeNetwork ?: return false
    val networkCapabilities = cm.getNetworkCapabilities(nw) ?: return false
    return when {
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) -> true
        networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) -> true
        else -> false
    }
}

fun logMessage(message: Any?, tag: String = "MyApp") {
    Logger.t(tag).e(message.toString())
}