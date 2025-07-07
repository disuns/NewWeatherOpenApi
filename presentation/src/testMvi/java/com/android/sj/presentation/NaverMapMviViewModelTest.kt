package com.android.sj.presentation

import android.content.Context
import app.cash.turbine.test
import com.android.sj.domain.ApiResult
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.models.NaverMapData
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.event.UiEvent
import com.android.sj.presentation.intent.NaverMapIntent
import com.android.sj.presentation.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.models.uimodels.navermap.ReverseGeoUIModel
import com.android.sj.presentation.viewmodels.NaverMapViewModel
import io.mockk.MockKAnnotations
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.every
import io.mockk.impl.annotations.MockK
import io.mockk.invoke
import io.mockk.verify
import junit.framework.TestCase.assertEquals
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Before
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class NaverMapMviViewModelTest {

    @MockK
    lateinit var getReverseGeoCoUseCase: GetReverseGeoCoUseCase

    @MockK
    lateinit var locationDataManager: LocationDataManager

    @MockK
    lateinit var mapper: NaverMapPresentationMapper

    @MockK(relaxed = true)
    lateinit var context: Context

    private lateinit var viewModel: NaverMapViewModel

    private val testDispatcher = StandardTestDispatcher()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        Dispatchers.setMain(testDispatcher)
        viewModel = NaverMapViewModel(
            getReverseGeoCoUseCase,
            locationDataManager,
            mapper,
            context
        )
    }

    @After
    fun tearDown() {
        Dispatchers.resetMain()
        clearAllMocks()
    }

    @Test
    fun `LoadNaverMapGeo 인텐트 시 호출 및 결과 성공`() = runTest {
        // Given
        val lon = 127.0
        val lat = 37.5
        val domainModel = NaverMapData(
            regionArea1Name = "1",
            regionArea2Name = "2",
            regionArea3Name = "3",
            landName = "12",
            landNumber = "13",
            resultName = "14",
            centerX = 10.0,
            centerY = 10.0,
        )

        val uiModel = ReverseGeoUIModel(
            mapAddress = "이미지 로딩중",
            centerX = domainModel.centerX.toString(),
            centerY = domainModel.centerY.toString()
        )

        coEvery { getReverseGeoCoUseCase("$lon,$lat") } returns flowOf(ApiResult.Success(domainModel))
        every { mapper.domainToUIReverseGeoCo(domainModel) } returns uiModel

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.effects.test {
                val first = awaitItem() as UiEvent.UpdateLocation
                assertEquals(lat, first.lat)
                assertEquals(lon, first.lon)

                val second = awaitItem() as UiEvent.UpdateLocation
                assertEquals(uiModel.mapAddress, second.address)
                assertEquals(uiModel.centerX, second.x)
                assertEquals(uiModel.centerY, second.y)

                cancelAndIgnoreRemainingEvents()
            }
        }

        // When
        viewModel.sendIntent(NaverMapIntent.LoadNaverMapGeo(lon, lat))
        advanceUntilIdle()

        // Then
        coVerify { getReverseGeoCoUseCase("$lon,$lat") }
        coVerify { mapper.domainToUIReverseGeoCo(domainModel) }
        job.cancel()
    }

    @Test
    fun `GetLocation 인텐트 시 GPS 콜백 및 LoadNaverMapGeo 인텐트 발생`() = runTest {
        // Given
        val lon = 127.1
        val lat = 37.6

        every {
            locationDataManager.getGps(
                onStopGps = any(),
                captureLambda()
            )
        } answers {
            firstArg<() -> Unit>().invoke()
            lambda<(Double, Double) -> Unit>().invoke(lat, lon)
        }

        coEvery { getReverseGeoCoUseCase("$lon,$lat") }
            .returns(flowOf(ApiResult.Empty))

        val job = launch(UnconfinedTestDispatcher()) {
            viewModel.effects.test {
                val evt = awaitItem() as UiEvent.UpdateLocation
                assertEquals(lat, evt.lat)
                assertEquals(lon, evt.lon)
                cancelAndIgnoreRemainingEvents()
            }
        }
        // When
        viewModel.sendIntent(NaverMapIntent.GetLocation)
        advanceUntilIdle()

        // Then
        verify { locationDataManager.getGps(onStopGps = any(), any()) }
        coVerify { getReverseGeoCoUseCase("$lon,$lat") }
        job.cancel()
    }
}