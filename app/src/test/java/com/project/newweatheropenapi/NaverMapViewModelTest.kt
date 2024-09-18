package com.project.newweatheropenapi

import android.content.Context
import app.cash.turbine.test
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import com.project.newweatheropenapi.network.repository.NaverMapRepository
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel
import io.mockk.clearAllMocks
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.StandardTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.After
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Before
import org.junit.Test
import org.mockito.Mockito

class NaverMapViewModelTest {

    private val repository: NaverMapRepository = mockk()
    private val locationDataManager : LocationDataManager = mockk()
    private lateinit var viewModel: NaverMapViewModel
    private lateinit var context: Context

    // 테스트 환경의 코루틴 디스패처 설정
    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setup() {
        Dispatchers.setMain(StandardTestDispatcher())
        context = Mockito.mock(Context::class.java)
        viewModel = NaverMapViewModel(repository, locationDataManager, context)
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
        val mockResponse = NaverMapResponse(
            status = NaverMapResponse.Status(
                code = 0,
                message = "done",
                name = "ok"
            ),
            results = listOf(
                NaverMapResponse.Result(
                    name = "addr",
                    code = NaverMapResponse.Result.Code(
                        id = "1156012400",
                        type = "L",
                        mappingId = "09560124"
                    ),
                    land = NaverMapResponse.Result.Land(
                        addition0 = NaverMapResponse.Result.Land.Addition(type = "", value = ""),
                        addition1 = NaverMapResponse.Result.Land.Addition(type = "", value = ""),
                        addition2 = NaverMapResponse.Result.Land.Addition(type = "", value = ""),
                        addition3 = NaverMapResponse.Result.Land.Addition(type = "", value = ""),
                        addition4 = NaverMapResponse.Result.Land.Addition(type = "", value = ""),
                        coords = NaverMapResponse.Coords(
                            center = NaverMapResponse.Coords.Center(
                                crs = "",
                                x = 0.0,
                                y = 0.0
                            )
                        ),
                        name = "",
                        number1 = "46",
                        number2 = "",
                        type = "1"
                    ),
                    region = NaverMapResponse.Result.Region(
                        area0 = NaverMapResponse.Result.Region.Area(
                            name = "kr",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "",
                                    x = 0.0,
                                    y = 0.0
                                )
                            )
                        ),
                        area1 = NaverMapResponse.Result.Region.Area(
                            name = "서울특별시",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "NHN:2048",
                                    x = 953935.4983318285,
                                    y = 1952044.0947760872
                                )
                            ),
                            alias = "서울"
                        ),
                        area2 = NaverMapResponse.Result.Region.Area(
                            name = "영등포구",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "NHN:2048",
                                    x = 946631.3184505092,
                                    y = 1947630.558633506
                                )
                            )
                        ),
                        area3 = NaverMapResponse.Result.Region.Area(
                            name = "문래동6가",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "NHN:2048",
                                    x = 945610.2230206165,
                                    y = 1946934.2022894057
                                )
                            )
                        ),
                        area4 = NaverMapResponse.Result.Region.Area(
                            name = "",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "",
                                    x = 0.0,
                                    y = 0.0
                                )
                            )
                        )
                    )
                ),
                NaverMapResponse.Result(
                    name = "roadaddr",
                    code = NaverMapResponse.Result.Code(
                        id = "1156012400",
                        type = "L",
                        mappingId = "09560124"
                    ),
                    land = NaverMapResponse.Result.Land(
                        addition0 = NaverMapResponse.Result.Land.Addition(type = "building", value = "현대아파트"),
                        addition1 = NaverMapResponse.Result.Land.Addition(type = "zipcode", value = "07283"),
                        addition2 = NaverMapResponse.Result.Land.Addition(type = "roadGroupCode", value = "115604154406"),
                        addition3 = NaverMapResponse.Result.Land.Addition(type = "", value = ""),
                        addition4 = NaverMapResponse.Result.Land.Addition(type = "", value = ""),
                        coords = NaverMapResponse.Coords(
                            center = NaverMapResponse.Coords.Center(
                                crs = "",
                                x = 0.0,
                                y = 0.0
                            )
                        ),
                        name = "문래로4길",
                        number1 = "14",
                        number2 = "",
                        type = ""
                    ),
                    region = NaverMapResponse.Result.Region(
                        area0 = NaverMapResponse.Result.Region.Area(
                            name = "kr",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "",
                                    x = 0.0,
                                    y = 0.0
                                )
                            )
                        ),
                        area1 = NaverMapResponse.Result.Region.Area(
                            name = "서울특별시",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "NHN:2048",
                                    x = 953935.4983318285,
                                    y = 1952044.0947760872
                                )
                            ),
                            alias = "서울"
                        ),
                        area2 = NaverMapResponse.Result.Region.Area(
                            name = "영등포구",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "NHN:2048",
                                    x = 946631.3184505092,
                                    y = 1947630.558633506
                                )
                            )
                        ),
                        area3 = NaverMapResponse.Result.Region.Area(
                            name = "문래동6가",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "NHN:2048",
                                    x = 945610.2230206165,
                                    y = 1946934.2022894057
                                )
                            )
                        ),
                        area4 = NaverMapResponse.Result.Region.Area(
                            name = "",
                            coords = NaverMapResponse.Coords(
                                center = NaverMapResponse.Coords.Center(
                                    crs = "",
                                    x = 0.0,
                                    y = 0.0
                                )
                            )
                        )
                    )
                )
            )
        )

        // MockK의 동작 정의
        coEvery { repository.getReverseGeoCo(any()) } returns flowOf(ApiResult.Success(mockResponse))

        // StateFlow 테스트
        viewModel.naverMapStateFlow.test {
            // 메서드 호출
            viewModel.fetchNaverMap(126.88237267230349,37.51982548626224)

            // 처음 상태는 Loading인지 확인
            assertTrue(awaitItem() is ApiResult.Loading)

            // 성공 상태 확인
            val successResult = awaitItem() as ApiResult.Success
            assertEquals(mockResponse, successResult.value)

            cancelAndIgnoreRemainingEvents()
        }
    }

    @Test
    fun `fetchNaverMap 실패 시 에러가 반환된다`() = runTest {
        // Mock 응답 객체 생성
        val errorMessage = "Error occurred"
        coEvery { repository.getReverseGeoCo(any()) } returns flowOf(ApiResult.Error(code = null, exception = Exception(errorMessage)))

        // StateFlow 테스트
        viewModel.naverMapStateFlow.test {
            // 메서드 호출
            viewModel.fetchNaverMap(126.88237267230349,37.51982548626224)

            // 처음 상태는 Loading인지 확인
            assertTrue(awaitItem() is ApiResult.Loading)

            // 에러 상태 확인
            val errorResult = awaitItem() as ApiResult.Error
            assertEquals(errorMessage, errorResult.exception?.message)

            cancelAndIgnoreRemainingEvents()
        }
    }
}