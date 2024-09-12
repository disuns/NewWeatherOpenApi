package com.project.newweatheropenapi.ui.compose.navermap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.geometry.LatLng
import com.naver.maps.geometry.LatLngBounds
import com.naver.maps.map.CameraPosition
import com.naver.maps.map.CameraUpdate
import com.naver.maps.map.compose.CameraPositionState
import com.naver.maps.map.compose.ExperimentalNaverMapApi
import com.naver.maps.map.compose.MapProperties
import com.naver.maps.map.compose.MapUiSettings
import com.naver.maps.map.compose.Marker
import com.naver.maps.map.compose.MarkerState
import com.naver.maps.map.compose.NaverMap
import com.naver.maps.map.compose.rememberCameraPositionState
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.utils.isNetworkCheck
import com.project.newweatheropenapi.utils.logMessage
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import com.project.newweatheropenapi.utils.managers.ComposeHelpManager
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen(
    onNavigate: () -> Unit = {},
    viewModel: NaverMapViewModel = hiltViewModel(),
    locationDataManager: LocationDataManager
) {
    val context = LocalContext.current

    val mapProperties = remember {
        MapProperties(
            maxZoom = 18.0,
            minZoom = 6.0,
            extent = LatLngBounds(LatLng(31.43, 122.37), LatLng(44.35, 132.0))
        )
    }

    val mapPosition = locationDataManager.locationData.collectAsState()
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        mapPosition.let {
            position = CameraPosition(it.value.latLng, 15.0)
        }
    }

//    ApiResultState(viewModel)

    // 최신 카메라 위치 추적
    val currentPosition by rememberUpdatedState(cameraPositionState.position.target)

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            if (context.isNetworkCheck()) {
                viewModel.fetchNaverMap(
                    cameraPositionState.position.target.longitude,
                    cameraPositionState.position.target.latitude
                )
            }

        }
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            properties = mapProperties,
            uiSettings = MapUiSettings(isLocationButtonEnabled = false),
            cameraPositionState = cameraPositionState
        ) {
            Marker(state = MarkerState(position = currentPosition))

        }
        MapSearchView(initQuery = mapPosition.value.address, onSearch = {}, onQueryChanged = {})

        //        moveCameraWithPosition(cameraPositionState= cameraPositionState, position = home)

    }
}

@Composable
fun ApiResultState(viewModel: NaverMapViewModel) {
    val naverMapState by viewModel.naverMapStateFlow.collectAsState()
    when (naverMapState) {
        is ApiResult.Loading -> {}
        is ApiResult.Success -> {
            logMessage((naverMapState as ApiResult.Success<NaverMapResponse>).value)
            viewModel.reverseGeocode((naverMapState as ApiResult.Success<NaverMapResponse>).value)
            //위 코드 작동 후 ui에 적용
        }

        is ApiResult.Error -> {
            logMessage(
                ("에러발생 : " + (naverMapState as ApiResult.Error).exception?.message)
            )
        }

        is ApiResult.Empty -> {

        }
    }
}

@Composable
fun MapSearchView(
    initQuery: String,
    onQueryChanged: (String) -> Unit,
    onSearch: (String) -> Unit
) {
    var query by remember { mutableStateOf(initQuery) }

    val searchViewPadding = ComposeHelpManager.previewDimenResource(
        resourceId = R.dimen.SearchViewPadding,
        hardcoding = 10.0f
    ).dp

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .wrapContentHeight()
            .padding(searchViewPadding, searchViewPadding, searchViewPadding)
    ) {
        TextField(
            value = query,
            onValueChange = { newValue ->
                query = newValue
                onQueryChanged(newValue)
            },
            placeholder = { Text("Search") },
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray), // 배경 색상 설정
            singleLine = true
        )

        Spacer(modifier = Modifier.height(8.dp))

        Button(
            onClick = { onSearch(query) }, // 검색 버튼 클릭 시 호출
            modifier = Modifier
                .align(Alignment.CenterHorizontally)
        ) {
            Text("Search")
        }
    }

    LaunchedEffect(initQuery) {
        query = initQuery
    }


//                val geocoder = Geocoder(LocalContext.current)
//
//                val addressList = geocoder.getFromLocationName(location, 1)
//                val address = addressList?.firstOrNull()
//
//                address?.let {
//                    markerSetCamPos(marker, it.latitude, it.longitude)
//                    naverMap.moveCamera(CameraUpdate.scrollTo(LatLng(it.latitude, it.longitude)))
//                }
}

@Preview
@Composable
private fun previewMapSearchNowView() {
    MapSearchView(
        initQuery = "테스트중",
        onQueryChanged = { logMessage("ff") },
        onSearch = { logMessage("ff") })
}

@OptIn(ExperimentalNaverMapApi::class)
private fun moveCameraWithPosition(position: LatLng, cameraPositionState: CameraPositionState) {
    val cameraUpdate = CameraUpdate.scrollTo(position)
    cameraPositionState.move(cameraUpdate)
}

@Preview
@Composable
private fun Preview() {
    Box(modifier = Modifier.fillMaxSize()) {
        previewMapSearchNowView()
        Box(modifier = Modifier.fillMaxSize())
    }
}