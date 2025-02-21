package com.android.sj.presentation.viewmodels

import android.content.Context
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.ApiResult
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetAirQualityUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetRltmStationUseCase
import com.android.sj.domain.usecase.usecaseinterface.airquality.GetStationFindUseCase
import com.android.sj.presentation.AirQualityPresentationMapperFactory
import com.android.sj.presentation.intent.AirQualityIntent
import com.android.sj.presentation.models.state.AirQualityViewState
import com.android.sj.presentation.utils.managers.TimeManager
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class AirQualityViewModel @Inject constructor(
    private val getAirQualityUseCase : GetAirQualityUseCase,
    private val getRltmStationUseCase : GetRltmStationUseCase,
    private val getStationFindUseCase : GetStationFindUseCase,
    mapperFactory: AirQualityPresentationMapperFactory
) : BaseViewModel<AirQualityViewState>(AirQualityViewState()) {
    private val mapper = mapperFactory.create(viewModelScope)

    fun handleIntent(intent: AirQualityIntent) {
        super.handleIntent(intent)
        when (intent) {
            is AirQualityIntent.LoadAllAirQuality -> fetchAllAirQualityData(intent.regionX, intent.regionY, intent.context)
            is AirQualityIntent.LoadAirQuality -> fetchAirQuality(intent.context)
            is AirQualityIntent.LoadRltmStation -> fetchRltmStation(intent.stationName)
            is AirQualityIntent.LoadStationFind -> fetchStationFindAndThenRltmStation(intent.regionX, intent.regionY)
        }
    }

    private fun fetchAllAirQualityData(
        regionX: String,
        regionY: String,
        context: Context
    ) {
        fetchAllData(
            { fetchAirQuality(context) },
            { fetchStationFindAndThenRltmStation(regionX, regionY) }
        )
    }

    private fun fetchStationFindAndThenRltmStation(regionX: String, regionY: String) {
        fetchData(mapper.domainToUIStationFind(getStationFindUseCase(regionX, regionY))) { currentState, result ->
            // 부수효과(side effect): 조건에 따라 추가 작업 수행
            if (result is ApiResult.Success && result.value.stationName != "정보없음") {
                fetchRltmStation(result.value.stationName)
            }
            // 상태 업데이트: stationFindState만 변경
            currentState.copy(stationFindState = result)
        }
    }

    private fun fetchAirQuality(context: Context) {
        fetchData(mapper.domainToUIAirQuality(getAirQualityUseCase(TimeManager(context).urlAirQualityDate()))) { currentState, result->
            currentState.copy(airQualityState = result)
        }
    }

    private fun fetchRltmStation(stationName: String) {
        fetchData(mapper.domainToUIRltmStation(getRltmStationUseCase(stationName))) { currentState, result->
            currentState.copy(rltmStationState = result)
        }
    }
}