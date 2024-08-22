package com.project.newweatheropenapi.app

import android.app.Application
import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkCapabilities
import android.os.Build
import android.os.Debug
import android.util.Log
import android.widget.Toast
import dagger.hilt.android.HiltAndroidApp

@HiltAndroidApp
class WeatherApplication:Application() {
    companion object{
        private lateinit var application: WeatherApplication
        fun getWeatherApplication() = application
    }

    override fun onCreate() {
        super.onCreate()
        application = this
    }

    fun isNetworkCheck() : Boolean{
        val cm = getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        val nw = cm.activeNetwork ?: return false
        val networkCapabilities = cm.getNetworkCapabilities(nw) ?: return false
        return when{
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) ->true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) ->true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_ETHERNET) ->true
            networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_BLUETOOTH) ->true
            else -> false
        }
    }

    fun toastMessage(text:String) = Toast.makeText(this,text, Toast.LENGTH_SHORT).show()
    fun debug(any: Any) = Log.d("Debug",any.toString())
}