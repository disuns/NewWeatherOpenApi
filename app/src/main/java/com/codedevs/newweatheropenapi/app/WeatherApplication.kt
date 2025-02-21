package com.codedevs.newweatheropenapi.app

import android.app.Application
import com.android.sj.common.LoggerInitializer
import dagger.hilt.android.HiltAndroidApp
import javax.inject.Inject

@HiltAndroidApp
class WeatherApplication:Application() {

    @Inject
    lateinit var loggerInitializer: LoggerInitializer
}