package com.project.newweatheropenapi.network.repository

import com.project.newweatheropenapi.network.service.WeatherService
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val service: WeatherService){
}