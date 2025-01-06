package com.android.sj.presentation

import android.content.Context
import app.cash.turbine.test
import com.android.sj.domain.ApiResult
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.domain.models.NaverMapData
import com.android.sj.domain.models.info.LocationInfo
import com.android.sj.domain.usecase.usecaseinterface.navermap.GetReverseGeoCoUseCase
import com.android.sj.presentation.intent.NaverMapIntent
import com.android.sj.presentation.mappers.NaverMapPresentationMapper
import com.android.sj.presentation.models.uimodels.navermap.ReverseGeoUIModel
import com.android.sj.presentation.viewmodels.NaverMapViewModel
import com.naver.maps.geometry.LatLng
import io.mockk.Runs
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.every
import io.mockk.just
import io.mockk.mockk
import junit.framework.TestCase.assertEquals
import junit.framework.TestCase.assertTrue
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import kotlinx.coroutines.withTimeout
import org.junit.After
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NaverMapViewModelTest {
    private val getReverseGeoCoUseCase: GetReverseGeoCoUseCase = mockk()
    private val locationDataManager: LocationDataManager = mockk()
    private val mapper: NaverMapPresentationMapper = mockk()
    private lateinit var viewModel: NaverMapViewModel
    private lateinit var context: Context

    // 테스트 환경의 코루틴 디스패처 설정
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        context = Mockito.mock(Context::class.java)
        viewModel = NaverMapViewModel(getReverseGeoCoUseCase, locationDataManager, mapper, context)

        // updateLocationData 모킹 설정 추가
        coEvery { locationDataManager.updateLocationData(any(), any(), any(), any(), any()) } just Runs

        val mockLocationData = LocationInfo(lat = 37.5206017, lng = 126.8825833)
        coEvery { locationDataManager.locationData } returns MutableStateFlow(mockLocationData)
    }

    // 테스트 후 디스패처 리셋
    @OptIn(ExperimentalCoroutinesApi::class)
    @After
    fun tearDown() {
        Dispatchers.resetMain()
        // 모든 모의 객체의 상태 초기화
        clearAllMocks()
    }

    @Test
    fun `fetchNaverMap 성공 시 데이터가 성공적으로 반환된다`() = runTest {
        // Mock 응답 객체 생성
        val mockReverseGeoUIData = mockk<ReverseGeoUIModel> {
            every { mapAddress } returns "Mocked Address"
            every { centerX } returns "Mocked centerX"
            every { centerY } returns "Mocked centerY"
        }
        val mockNaverMapData = mockk<NaverMapData>()
        val mockApiResult = ApiResult.Success(mockReverseGeoUIData)
        val mockDomainResult = ApiResult.Success(mockNaverMapData)

        coEvery { getReverseGeoCoUseCase(any()) } returns flowOf(mockDomainResult)
        coEvery { mapper.domainToUIReverseGeoCo(any()) } returns flowOf(mockApiResult)

        // StateFlow 테스트
        viewModel.state.test {
            // 메서드 호출
            withTimeout(5000) {
                viewModel.handleIntent(
                    NaverMapIntent.LoadNaverMapGeo(126.8825833, 37.5206017)
                )
            }

            // 처음 상태는 Loading인지 확인
            assertTrue(awaitItem().naverMapState is ApiResult.Loading)

            // 성공 상태 확인
            val successResult = awaitItem().naverMapState as ApiResult.Success
            assertEquals(mockApiResult.value, successResult.value)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchNaverMap 실패 시 에러가 반환된다`() = runTest {
        // Mock 응답 객체 생성
        val errorMessage = "Error"
        val errorResult = ApiResult.Error(
            code = null,
            exception = Exception(errorMessage)
        )

        // Mock 설정
        coEvery { getReverseGeoCoUseCase(any()) } returns flowOf(errorResult)
        coEvery { mapper.domainToUIReverseGeoCo(any()) } returns flowOf(errorResult)

        // StateFlow 테스트
        viewModel.state.test {
            // 메서드 호출
            withTimeout(5000) {
                viewModel.handleIntent(
                    NaverMapIntent.LoadNaverMapGeo(126.8825833, 37.5206017)
                )
            }
            // 처음 상태는 Loading인지 확인
            assertTrue(awaitItem().naverMapState is ApiResult.Loading)

            // 에러 상태 확인
            val state = awaitItem()
            assertTrue(state.naverMapState is ApiResult.Error)
            val errorResult = state.naverMapState as ApiResult.Error
            assertEquals(errorMessage, errorResult.exception?.message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}