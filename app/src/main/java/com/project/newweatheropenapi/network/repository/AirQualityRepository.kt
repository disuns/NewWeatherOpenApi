package com.project.newweatheropenapi.network.repository

import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.service.AirQualityService
import com.sjchoi.weather.dataclass.datapotal.indexdata.AirQualityIndex
import kotlinx.coroutines.flow.Flow
import retrofit2.Response
import javax.inject.Inject

class AirQualityRepository @Inject constructor(private val service: AirQualityService){
//     fun requestAirQuality(serviceKey: String, returnType:String, pageNo:String, numOfRows:String, searchDate:String, InformCode:String): Flow<ApiResult<>> {
//
//     }
}