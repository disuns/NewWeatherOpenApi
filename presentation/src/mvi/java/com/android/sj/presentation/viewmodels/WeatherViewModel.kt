package com.android.sj.presentation.viewmodels

import android.content.Context
import com.android.sj.domain.usecase.usecaseinterface.weather.GetTimeWeatherUseCase
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeatherUseCase
import com.android.sj.domain.usecase.usecaseinterface.weather.GetWeekRainSkyUseCase
import com.android.sj.presentation.common.mappers.WeatherPresentationMapper
import com.android.sj.presentation.common.state.uistate.BaseUiState
import com.android.sj.presentation.common.state.viewstate.WeatherViewState
import com.android.sj.presentation.intent.WeatherIntent
import com.android.sj.presentation.partialstate.WeatherPartialState
import com.android.sj.presentation.utils.convertGRIDGPS
import com.android.sj.presentation.utils.landCodeGu
import com.android.sj.presentation.utils.managers.TimeManager
import com.naver.maps.geometry.LatLng
import dagger.hilt.android.lifecycle.HiltViewModel
import dagger.hilt.android.qualifiers.ApplicationContext
import javax.inject.Inject

@HiltViewModel
class WeatherViewModel @Inject constructor(
    private val getWeatherUseCase: GetWeatherUseCase,
    private val getTimeWeatherUseCase: GetTimeWeatherUseCase,
    private val getWeekRainSkyUseCase: GetWeekRainSkyUseCase,
    private val mapper : WeatherPresentationMapper,
    private val timeManager: TimeManager,
    @ApplicationContext val context: Context
) : BaseViewModel<WeatherIntent, WeatherViewState, WeatherPartialState>(WeatherViewState()) {

    override suspend fun handleIntent(intent: WeatherIntent) {
        when(intent){
            is WeatherIntent.LoadAllWeather -> {
                sendIntent(WeatherIntent.LoadWeather(intent.nx, intent.ny))
                sendIntent(WeatherIntent.LoadTimeWeather(intent.nx, intent.ny))
                sendIntent(WeatherIntent.LoadWeekRainSky(intent.address))
            }
            is WeatherIntent.LoadWeather-> fetchWeather(intent.nx, intent.ny)
            is WeatherIntent.LoadTimeWeather-> fetchTimeWeather(intent.nx, intent.ny)
            is WeatherIntent.LoadWeekRainSky -> fetchWeekRainSky(intent.address)
        }
    }

    override fun reduceState(
        current: WeatherViewState,
        partial: WeatherPartialState
    ): WeatherViewState {
        return when(partial){
            is WeatherPartialState.LoadingWeather ->
                current.copy(weatherUiState = BaseUiState(isLoading = true))
            is WeatherPartialState.WeatherSuccess ->
                current.copy(weatherUiState = BaseUiState(model = partial.data))
            is WeatherPartialState.WeatherError ->
                current.copy(
                    weatherUiState = BaseUiState(
                        isError = true,
                        errorMessage = partial.message,
                        errorCode = partial.code
                    )
                )
            is WeatherPartialState.WeatherEmpty -> current.copy(
                weatherUiState = BaseUiState(
                    isEmptyData = true,
                    errorMessage = partial.message
                )
            )
            is WeatherPartialState.LoadingTimeWeather ->
                current.copy(timeWeatherUiState = BaseUiState(isLoading = true))
            is WeatherPartialState.TimeWeatherSuccess ->
                current.copy(timeWeatherUiState = BaseUiState(model = partial.data))
            is WeatherPartialState.TimeWeatherError ->
                current.copy(
                    timeWeatherUiState = BaseUiState(
                        isError = true,
                        errorMessage = partial.message,
                        errorCode = partial.code
                    )
                )
            is WeatherPartialState.TimeWeatherEmpty -> current.copy(
                timeWeatherUiState = BaseUiState(
                    isEmptyData = true,
                    errorMessage = partial.message
                )
            )
            is WeatherPartialState.LoadingWeekRainSky ->
                current.copy(weekRainSkyUiState = BaseUiState(isLoading = true))
            is WeatherPartialState.WeekRainSkySuccess ->
                current.copy(weekRainSkyUiState = BaseUiState(model = partial.data))
            is WeatherPartialState.WeekRainSkyError ->
                current.copy(
                    weekRainSkyUiState = BaseUiState(
                        isError = true,
                        errorMessage = partial.message,
                        errorCode = partial.code
                    )
                )
            is WeatherPartialState.WeekRainSkyEmpty -> current.copy(
                weekRainSkyUiState = BaseUiState(
                    isEmptyData = true,
                    errorMessage = partial.message
                )
            )
        }
    }

    private fun fetchWeather(
        nx: String,
        ny: String
    ) {
        val (lat, lon) = convertCoordinates(nx, ny)

        fetchAndReduce(
            usecase = getWeatherUseCase(
                timeManager.urlNowDate(),
                timeManager.urlNowTime(),
                lat,
                lon
            ),
            mapper = mapper::domainToUIWeather,
            emitLoading = WeatherPartialState.LoadingWeather,
            emitEmpty = WeatherPartialState.WeatherEmpty("Empty Data"),
            emitError = { msg, code -> WeatherPartialState.WeatherError(msg, code) },
            emitSuccess = { model -> WeatherPartialState.WeatherSuccess(model) }
        )
    }

    private fun fetchTimeWeather(
        nx: String,
        ny: String
    ) {
        val (lat, lon) = convertCoordinates(nx, ny)

        fetchAndReduce(
            usecase = getTimeWeatherUseCase(timeManager.urlTimeWeatherDate(), timeManager.urlTimeWeatherTime(), lat, lon),
            mapper = mapper::domainToUITimeWeather,
            emitLoading = WeatherPartialState.LoadingTimeWeather,
            emitEmpty = WeatherPartialState.TimeWeatherEmpty("Empty Data"),
            emitError = { msg, code -> WeatherPartialState.TimeWeatherError(msg, code) },
            emitSuccess = { model -> WeatherPartialState.TimeWeatherSuccess(model) }
        )

    }

    private fun fetchWeekRainSky(regId: String) {
        val landCode = regId.landCodeGu(context = context)

        fetchAndReduce(
            usecase = getWeekRainSkyUseCase(landCode, timeManager.urlWeekWeatherTime()),
            mapper = mapper::domainToUIWeekRainSky,
            emitLoading = WeatherPartialState.LoadingWeekRainSky,
            emitEmpty = WeatherPartialState.WeekRainSkyEmpty("Empty Data"),
            emitError = { msg, code -> WeatherPartialState.WeekRainSkyError(msg, code) },
            emitSuccess = { model -> WeatherPartialState.WeekRainSkySuccess(model) }
        )
    }

    private fun convertCoordinates(nx: String, ny: String): Pair<String, String> {
        val convertLatLng = LatLng(nx.toDouble(), ny.toDouble()).convertGRIDGPS(0)
        val lat = convertLatLng.latitude.toInt().toString()
        val lon = convertLatLng.longitude.toInt().toString()
        return Pair(lat, lon)
    }
}