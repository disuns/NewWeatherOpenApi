package com.project.newweatheropenapi.ui.compose.navermap

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
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
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
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
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import com.project.newweatheropenapi.utils.isNetworkCheck
import com.project.newweatheropenapi.utils.logMessage
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
        MapSearchView(initQuery = mapPosition.value.address, onSearch = {})

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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MapSearchView(
    initQuery: String,
    onSearch: (String) -> Unit
) {
    var query by remember { mutableStateOf(initQuery) }

    val searchViewPadding = dimensionResource(R.dimen.SearchViewPadding)

    Box(Modifier.background(Color.Transparent).padding()
        .padding(start = searchViewPadding, end = searchViewPadding, top = searchViewPadding)){
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(Color.Gray)
                .padding(start = searchViewPadding),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = null,
                tint = Color.Black,
            )
            Spacer(modifier = Modifier.width(searchViewPadding))
            BasicTextField(
                value = query,
                onValueChange = { newValue ->
                    query = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Gray)
                    .weight(1f),
                singleLine = true,
                keyboardActions = KeyboardActions (
                    onSearch = {onSearch(query)}
                ),
            )

            IconButton(
                onClick = { query = "" },
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_baseline_cancel_24),
                    tint = Color.Black,
                    contentDescription = null,
                )
            }
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
private fun PreviewMapSearchNowView() {
    MapSearchView(
        initQuery = "테스트중",
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
        PreviewMapSearchNowView()
        Box(modifier = Modifier.fillMaxSize())
    }
}