package com.project.newweatheropenapi

import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.datapotal.WeatherResponse
import com.project.newweatheropenapi.network.repository.WeatherRepository
import com.project.newweatheropenapi.network.service.WeatherService
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import okhttp3.ResponseBody
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.ArgumentMatchers.anyMap
import org.mockito.Mockito.mock
import org.mockito.Mockito.`when`
import retrofit2.Response

@ExperimentalCoroutinesApi
class WeatherRepositoryTest {
    private lateinit var weatherRepository: WeatherRepository
    private lateinit var weatherService: WeatherService

    @Before
    fun setup() {
        weatherService = mock(WeatherService::class.java)
        weatherRepository = WeatherRepository(weatherService)
    }

//    @Test
//    fun `getWeather returns Success when response is successful`() = runTest {
//        // Given
//        val weatherResponse = WeatherResponse()
//        val response = Response.success(weatherResponse)
//        `when`(weatherService.getWeather(anyMap())).thenReturn(response)
//
//        // When
//        val result = weatherRepository.getWeather(mapOf()).first()
//
//        // Then
//        assertTrue(result is ApiResult.Success)
//        assertTrue((result as ApiResult.Success).value == weatherResponse)
//    }

    @Test
    fun `getWeather returns Error when response is unsuccessful`() = runTest {
        // Given
        val response = Response.error<WeatherResponse>(500, mock(ResponseBody::class.java))
        `when`(weatherService.getWeather(anyMap())).thenReturn(response)

        // When
        val result = weatherRepository.getWeather(mapOf()).first()

        // Then
        assertTrue(result is ApiResult.Error)
        assertTrue((result as ApiResult.Error).code == 500)
    }
}