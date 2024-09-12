package com.project.newweatheropenapi.ui.compose.weather

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ui.theme.Color_bbdefb
import com.project.newweatheropenapi.utils.managers.ComposeHelpManager
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.utils.managers.TimeManager
import com.project.newweatheropenapi.viewmodel.WeatherViewModel
import java.sql.Time

@Composable
fun WeatherScreen(
    onNavigate: () -> Unit = {},
    viewModel: WeatherViewModel = hiltViewModel(),
    locationDataManager: LocationDataManager
) {
    val locationData = locationDataManager.locationData.collectAsState()
    val address = locationData.value.address

    LaunchedEffect(address) {
        if (address.isNotEmpty()) {
            viewModel.fetchAllWeatherData(
                nx = locationData.value.latLng.latitude.toString(),
                ny = locationData.value.latLng.longitude.toString(),
                address = address
            )
        }
    }

    WeatherScreenUI(onNavigate, address, viewModel)
}

@Preview
@Composable
private fun Preview(/*@PreviewParameter(LocationDataManagerPreviewParameterProvider::class) locationDataManager: LocationDataManager*/) {
//    locationDataManager.updateLocationData(
//        LatLng(37.51982548626224, 126.88237267230349),
//        "서울이다"
//    )
//    WeatherScreen(locationDataManager = locationDataManager, viewModel = viewModel)

    val backgroundColor = Color_bbdefb
    val paddingStart = ComposeHelpManager.previewDimenResource(R.dimen.PaddingStart, 14.0f)
    val paddingEnd = ComposeHelpManager.previewDimenResource(R.dimen.PaddingEnd, 10.0f)

//    Column(
//        modifier = Modifier
//            .fillMaxSize()
//            .background(backgroundColor)
//    ) {
//        TopLocationButton(
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f)
//        ){}
//        Column(
//            Modifier
//                .fillMaxWidth()
//                .weight(12f)
//                .padding(start = paddingStart.dp, end = paddingEnd.dp)
//        ) {
//            TitleColumn()
//            NowWeatherColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(4f),
//                weatherState = weatherState
//            )
//            TimeWeatherColumn(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(4f)
//            )
//            WeekForecastSection(
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .weight(3f)
//            )
//        }
//    }
}

//class LocationDataManagerPreviewParameterProvider(
//    override val values: Sequence<LocationDataManager> = sequenceOf(
//        LocationDataManager(object : FusedLocationProviderClient {
//            // FusedLocationProviderClient의 모든 메서드에 대해 간단한 더미 구현을 제공합니다.
//            override fun getApiKey(): ApiKey<Api.ApiOptions.NoOptions> {
//                TODO()
//            }
//
//            override fun getLastLocation(): Task<Location> {
//                TODO()
//            }
//
//            override fun getLastLocation(p0: LastLocationRequest): Task<Location> {
//                TODO()
//            }
//
//            override fun getCurrentLocation(p0: Int, p1: CancellationToken?): Task<Location> {
//                TODO()
//            }
//
//            override fun getCurrentLocation(
//                p0: CurrentLocationRequest,
//                p1: CancellationToken?
//            ): Task<Location> {
//                TODO()
//            }
//
//            override fun getLocationAvailability(): Task<LocationAvailability> {
//                TODO()
//            }
//
//            override fun requestLocationUpdates(
//                p0: LocationRequest,
//                p1: Executor,
//                p2: LocationListener
//            ): Task<Void> {
//                TODO()
//            }
//
//            override fun requestLocationUpdates(
//                p0: LocationRequest,
//                p1: LocationListener,
//                p2: Looper?
//            ): Task<Void> {
//                TODO()
//            }
//
//            override fun requestLocationUpdates(
//                p0: LocationRequest,
//                p1: LocationCallback,
//                p2: Looper?
//            ): Task<Void> {
//                TODO()
//            }
//
//            override fun requestLocationUpdates(
//                p0: LocationRequest,
//                p1: Executor,
//                p2: LocationCallback
//            ): Task<Void> {
//                TODO()
//            }
//
//            override fun requestLocationUpdates(
//                p0: LocationRequest,
//                p1: PendingIntent
//            ): Task<Void> {
//                TODO()
//            }
//
//            override fun removeLocationUpdates(p0: LocationListener): Task<Void> {
//                TODO()
//            }
//
//            override fun removeLocationUpdates(p0: LocationCallback): Task<Void> {
//                TODO()
//            }
//
//            override fun removeLocationUpdates(p0: PendingIntent): Task<Void> {
//                TODO()
//            }
//
//            override fun flushLocations(): Task<Void> {
//                TODO()
//            }
//
//            override fun setMockMode(p0: Boolean): Task<Void> {
//                TODO()
//            }
//
//            override fun setMockLocation(p0: Location): Task<Void> {
//                TODO()
//            }
//
//            override fun requestDeviceOrientationUpdates(
//                p0: DeviceOrientationRequest,
//                p1: Executor,
//                p2: DeviceOrientationListener
//            ): Task<Void> {
//                TODO()
//            }
//
//            override fun requestDeviceOrientationUpdates(
//                p0: DeviceOrientationRequest,
//                p1: DeviceOrientationListener,
//                p2: Looper?
//            ): Task<Void> {
//                TODO()
//            }
//
//            override fun removeDeviceOrientationUpdates(p0: DeviceOrientationListener): Task<Void> {
//                TODO()
//            }
//        })
//    )
//) : PreviewParameterProvider<LocationDataManager>