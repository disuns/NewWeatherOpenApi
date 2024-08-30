package com.project.newweatheropenapi.ui.compose.navermap

import android.util.Log
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.semantics.Role.Companion.Button
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
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen(
    onNavigate : ()->Unit = {},
    viewModel: NaverMapViewModel = hiltViewModel()) {

    val mapProperties by remember {
        mutableStateOf(
            MapProperties(maxZoom = 18.0, minZoom = 5.0)
        )
    }

    val mapPosition by remember {
        mutableStateOf(
            LatLng(37.532600, 127.024612)
        )
    }

    val mapUiSettings = MapUiSettings(isLocationButtonEnabled = false)

    val home = LatLng(37.51982548626224, 126.88237267230349)
    val cameraPositionState: CameraPositionState = rememberCameraPositionState {
        position = CameraPosition(mapPosition, 18.0)
    }

    Box(Modifier.fillMaxSize()) {
        NaverMap(properties = mapProperties, uiSettings = mapUiSettings, cameraPositionState = cameraPositionState){
            Marker(
                state = MarkerState(position = cameraPositionState.position.target)
            )
        }

        Column(modifier = Modifier.fillMaxWidth(),
            verticalArrangement = Arrangement.Bottom) {
            Button(onClick = {
                moveCameraWithPosition(cameraPositionState= cameraPositionState, position = home)
            },
                modifier =  Modifier.fillMaxHeight()){}
        }
    }
}

@OptIn(ExperimentalNaverMapApi::class)
private fun moveCameraWithPosition(position:LatLng, cameraPositionState: CameraPositionState){
    val cameraUpdate = CameraUpdate.scrollTo(position)
    cameraPositionState.move(cameraUpdate)
}

@Preview
@Composable
private fun Preview(){
    NaverMapScreen()
}