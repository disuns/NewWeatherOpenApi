package com.project.newweatheropenapi.ui.compose.navermap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberUpdatedState
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.naver.maps.geometry.LatLng
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
import com.project.newweatheropenapi.common.isNetworkCheck
import com.project.newweatheropenapi.common.logMessage
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.network.dataclass.response.navermap.NaverMapResponse
import com.project.newweatheropenapi.viewmodel.ActivityViewModel
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen(
    onNavigate : ()->Unit = {},
    activityViewModel: ActivityViewModel,
    viewModel: NaverMapViewModel = hiltViewModel(),
    mapPosition: LatLng = LatLng(37.51982548626224, 126.88237267230349)) {

    val context= LocalContext.current
    val mapProperties = remember {MapProperties(maxZoom = 18.0, minZoom = 5.0) }

    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(mapPosition, 18.0)
    }

    // 최신 카메라 위치 추적
    val currentPosition by rememberUpdatedState(cameraPositionState.position.target)

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!cameraPositionState.isMoving) {
            val lnglat = "${cameraPositionState.position.target.longitude},${cameraPositionState.position.target.latitude}"
            if(context.isNetworkCheck()){
                viewModel.fetchNaverMap(lnglat)
            }

        }
    }

    Box(Modifier.fillMaxSize()) {
        NaverMap(properties = mapProperties, uiSettings = MapUiSettings(isLocationButtonEnabled = false), cameraPositionState = cameraPositionState){
            Marker(state = MarkerState(position = currentPosition))
        }
        ApiResultState(viewModel)

//        moveCameraWithPosition(cameraPositionState= cameraPositionState, position = home)
    }
}

@Composable
fun ApiResultState(viewModel: NaverMapViewModel){
    val naverMapState by viewModel.naverMapStateFlow.collectAsState()
    when (naverMapState) {
        is ApiResult.Loading -> {}
        is ApiResult.Success -> {
            logMessage((naverMapState as ApiResult.Success<NaverMapResponse>).value)
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

//@OptIn(ExperimentalMaterial3Api::class)
//@Composable
//fun MapSearchView(
//    query : String,
//    onQueryChanged : (String) -> Unit,
//    onSearch:(String)->Unit,
//    modifier: Modifier = Modifier){
//
//    var isActive by remember{ mutableStateOf(false) }
//    SearchBar(query = query,
//        onQueryChange = {onQueryChanged(it)},
//        onSearch = {location->
//            if(location.isNotEmpty()){
////                val geocoder = Geocoder(LocalContext.current)
////
////                val addressList = geocoder.getFromLocationName(location, 1)
////                val address = addressList?.firstOrNull()
////
////                address?.let {
////                    markerSetCamPos(marker, it.latitude, it.longitude)
////                    naverMap.moveCamera(CameraUpdate.scrollTo(LatLng(it.latitude, it.longitude)))
////                }
//            }
//        },
//        active = isActive,
//        onActiveChange = { isActive = it },
//        placeholder = { Text("Search") },
//        modifier = modifier
//    )
//}

@OptIn(ExperimentalNaverMapApi::class)
private fun moveCameraWithPosition(position:LatLng, cameraPositionState: CameraPositionState){
    val cameraUpdate = CameraUpdate.scrollTo(position)
    cameraPositionState.move(cameraUpdate)
}

@Preview
@Composable
private fun Preview(){
    NaverMapScreen(activityViewModel = ActivityViewModel())
}