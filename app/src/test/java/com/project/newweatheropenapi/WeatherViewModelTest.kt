import com.project.newweatheropenapi.network.repository.WeatherRepository
import com.project.newweatheropenapi.viewmodel.WeatherViewModel
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.mockito.Mockito.mock

@ExperimentalCoroutinesApi
class WeatherViewModelTest {

    private lateinit var viewModel: WeatherViewModel
    private lateinit var weatherRepository: WeatherRepository

    @Before
    fun setup() {
        weatherRepository = mock(WeatherRepository::class.java)
        viewModel = WeatherViewModel(weatherRepository)
    }

//    @Test
//    fun `fetchWeather updates state flow to Success`() = runTest {
//        // Given
//        val weatherResponse = WeatherResponse()
//        `when`(weatherRepository.getWeather(anyMap())).thenReturn(flowOf(ApiResult.Success(weatherResponse)))
//
//        // When
//        viewModel.fetchWeather(mapOf())
//        val result = viewModel.weatherStateFlow.first()
//
//        // Then
//        assertTrue(result is ApiResult.Success)
//        assertTrue((result as ApiResult.Success).value == weatherResponse)
//    }

//    @Test
//    fun `fetchWeather updates state flow to Error`() = runTest {
//        // Given
//        `when`(weatherRepository.getWeather(anyMap())).thenReturn(flowOf(ApiResult.Error(code = 500)))
//
//        // When
//        viewModel.fetchWeather(mapOf())
//        val result = viewModel.weatherStateFlow.first()
//
//        // Then
//        assertTrue(result is ApiResult.Error)
//        assertTrue((result as ApiResult.Error).code == 500)
//    }
}