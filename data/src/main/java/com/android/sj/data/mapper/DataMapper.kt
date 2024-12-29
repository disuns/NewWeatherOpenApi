package com.test.data.mapper

import com.android.sj.data.remote.response.datapotal.AirQualityResponse
import com.android.sj.data.remote.response.datapotal.RltmStationResponse
import com.android.sj.data.remote.response.datapotal.StationFindResponse
import com.android.sj.data.remote.response.datapotal.WeatherResponse
import com.android.sj.data.remote.response.datapotal.WeekRainSkyResponse
import com.android.sj.data.remote.response.navermap.NaverMapResponse
import com.android.sj.domain.models.AirQualityData
import com.android.sj.domain.models.NaverMapData
import com.android.sj.domain.models.RltmStationData
import com.android.sj.domain.models.StationFindData
import com.android.sj.domain.models.WeatherData
import com.android.sj.domain.models.WeekRainSkyData
import com.test.domain.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataMapper @Inject constructor() {
    fun responseToDomainAirQuality(response : Flow<ApiResult<AirQualityResponse>>): Flow<ApiResult<AirQualityData>> {
        return response.map { flow->
            when(flow){
                is ApiResult.Success -> ApiResult.Success(AirQualityData())
                is ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Loading -> ApiResult.Loading
                is ApiResult.Error -> ApiResult.Error(flow.code, flow.exception)
            }
        }
    }
    fun responseToDomainRltmStation(response : Flow<ApiResult<RltmStationResponse>>): Flow<ApiResult<RltmStationData>> {
        return response.map { flow->
            when(flow){
                is ApiResult.Success -> ApiResult.Success(RltmStationData())
                is ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Loading -> ApiResult.Loading
                is ApiResult.Error -> ApiResult.Error(flow.code, flow.exception)
            }
        }
    }
    fun responseToDomainStationFind(response : Flow<ApiResult<StationFindResponse>>): Flow<ApiResult<StationFindData>> {
        return response.map { flow->
            when(flow){
                is ApiResult.Success -> ApiResult.Success(StationFindData())
                is ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Loading -> ApiResult.Loading
                is ApiResult.Error -> ApiResult.Error(flow.code, flow.exception)
            }
        }
    }
    fun responseToDomainReverseGeoCo(response : Flow<ApiResult<NaverMapResponse>>): Flow<ApiResult<NaverMapData>> {
        return response.map { flow->
            when(flow){
                is ApiResult.Success -> ApiResult.Success(NaverMapData())
                is ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Loading -> ApiResult.Loading
                is ApiResult.Error -> ApiResult.Error(flow.code, flow.exception)
            }
        }
    }
    fun responseToDomainWeather(response : Flow<ApiResult<WeatherResponse>>): Flow<ApiResult<WeatherData>> {
        return response.map { flow->
            when(flow){
                is ApiResult.Success -> ApiResult.Success(WeatherData())
                is ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Loading -> ApiResult.Loading
                is ApiResult.Error -> ApiResult.Error(flow.code, flow.exception)
            }
        }
    }
    fun responseToDomainTimeWeather(response : Flow<ApiResult<WeatherResponse>>): Flow<ApiResult<WeatherData>> {
        return response.map { flow->
            when(flow){
                is ApiResult.Success -> ApiResult.Success(WeatherData())
                is ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Loading -> ApiResult.Loading
                is ApiResult.Error -> ApiResult.Error(flow.code, flow.exception)
            }
        }
    }
    fun responseToDomainRainSky(response : Flow<ApiResult<WeekRainSkyResponse>>): Flow<ApiResult<WeekRainSkyData>> {
        return response.map { flow->
            when(flow){
                is ApiResult.Success -> ApiResult.Success(WeekRainSkyData())
                is ApiResult.Empty -> ApiResult.Empty
                is ApiResult.Loading -> ApiResult.Loading
                is ApiResult.Error -> ApiResult.Error(flow.code, flow.exception)
            }
        }
    }
}