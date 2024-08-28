package com.project.newweatheropenapi.network.repository

import com.project.newweatheropenapi.DATA_POTAL_SERVICE_KEY
import com.project.newweatheropenapi.DATA_TYPE_UPPER
import com.project.newweatheropenapi.NUM_OF_ROWS_DEFAULT
import com.project.newweatheropenapi.NUM_OF_ROWS_WEEK
import com.project.newweatheropenapi.PAGE_NO_DEFAULT
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.safeFlow
import com.project.newweatheropenapi.network.service.WeatherService
import com.sjchoi.weather.dataclass.datapotal.fcstdata.FcstData
import kotlinx.coroutines.flow.flow
import retrofit2.http.Query
import javax.inject.Inject

class WeatherRepository @Inject constructor(private val service: WeatherService){
    fun getWeather(@Query("base_date") base_date:String,
                   @Query("base_time") base_time:String,
                   @Query("nx") nx:String,
                   @Query("ny") ny:String)= flow{
        emit(safeFlow { service.getWeather(DATA_POTAL_SERVICE_KEY,PAGE_NO_DEFAULT,NUM_OF_ROWS_DEFAULT,DATA_TYPE_UPPER, base_date, base_time, nx, ny) })
    }

    fun getNowWeather(@Query("base_date") base_date:String,
                      @Query("base_time") base_time:String,
                      @Query("nx") nx:String,
                      @Query("ny") ny:String) = flow{
        emit(safeFlow{service.getNowWeather(DATA_POTAL_SERVICE_KEY, PAGE_NO_DEFAULT, NUM_OF_ROWS_DEFAULT, DATA_TYPE_UPPER, base_date, base_time, nx, ny)})
    }

    fun getWeekRainSky(@Query("regId") regId:String,
                       @Query("tmFc") tmFc:String) = flow{
        emit(safeFlow{service.getWeekRainSky(DATA_POTAL_SERVICE_KEY, PAGE_NO_DEFAULT, NUM_OF_ROWS_WEEK, DATA_TYPE_UPPER, regId, tmFc)})
    }
}