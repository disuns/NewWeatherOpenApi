package com.project.newweatheropenapi.ui.compose.navermap

import android.content.Context
import android.location.Geocoder
import android.os.Build
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
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
import com.project.newweatheropenapi.ui.theme.icon.CancelImageVector
import com.project.newweatheropenapi.ui.theme.icon.SearchImageVector
import com.project.newweatheropenapi.utils.isNetworkCheck
import com.project.newweatheropenapi.utils.logMessage
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.utils.sp
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel
import java.io.IOException

@OptIn(ExperimentalNaverMapApi::class)
@Composable
fun NaverMapScreen(
    viewModel: NaverMapViewModel,
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
    var isInitialLoad by remember { mutableStateOf(true) }

    LaunchedEffect(cameraPositionState.isMoving) {
        if (!isInitialLoad && !cameraPositionState.isMoving && context.isNetworkCheck()) {
            viewModel.fetchNaverMap(
                cameraPositionState.position.target.longitude,
                cameraPositionState.position.target.latitude
            )
        }
        isInitialLoad = false
    }

    Box(modifier = Modifier.fillMaxSize()) {
        NaverMap(
            properties = mapProperties,
            uiSettings = MapUiSettings(isLocationButtonEnabled = false),
            cameraPositionState = cameraPositionState
        ) {
            Marker(state = MarkerState(position = cameraPositionState.position.target))
        }
        MapSearchView(initQuery = mapPosition.value.address, onSearch = { query ->
            if (query.isNotEmpty()) {
                addressAndMoveCamera(query, cameraPositionState, context)
            }
        })
    }
}

private fun addressAndMoveCamera(query: String, cameraPositionState: CameraPositionState, context: Context) {
    val geocoder = Geocoder(context)

    try {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            geocoder.getFromLocationName(query, 1) { addressList ->
                addressList.firstOrNull()?.let { address ->
                    moveCameraWithPosition(
                        cameraPositionState = cameraPositionState,
                        position = LatLng(address.latitude, address.longitude)
                    )
                }
            }
        } else {
            val addressList = geocoder.getFromLocationName(query, 1)
            if (!addressList.isNullOrEmpty()) {
                val address = addressList[0]
                moveCameraWithPosition(
                    cameraPositionState = cameraPositionState,
                    position = LatLng(address.latitude, address.longitude)
                )
            }
        }
    } catch (e: IOException) {
        e.stackTrace
    }
}

@Composable
fun MapSearchView(
    initQuery: String,
    onSearch: (String) -> Unit
) {
    var query by remember { mutableStateOf(initQuery) }

    Box(
        Modifier
            .fillMaxWidth()
            .background(brush = Brush.verticalGradient(
                colors = listOf(Color.Black, Color.Transparent),
                startY = 0f,
                endY = Float.POSITIVE_INFINITY
            ))
            .padding(horizontal = 16.dp, vertical = 12.5.dp)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .background(
                    color = Color(0x4DFFFFFF),
                    shape = RoundedCornerShape(999.dp)
                )
                .align(Alignment.Center),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Icon(
                imageVector = SearchImageVector,
                contentDescription = null,
                modifier = Modifier
                    .size(40.dp)
                    .padding(start = 12.dp, end = 8.dp),
                tint = Color.White
            )
            BasicTextField(
                value = query,
                onValueChange = { newValue ->
                    query = newValue
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .background(Color.Transparent)
                    .weight(1f),
                singleLine = true,
                keyboardActions = KeyboardActions(
                    onDone = { onSearch(query) }
                ),
                textStyle = TextStyle(color = Color.White,
                    fontSize = dimensionResource(id = R.dimen.MainText).sp())
            )

            IconButton(
                onClick = { query = "" },
            ) {
                Icon(
                    imageVector = CancelImageVector,
                    tint = Color.Black,
                    contentDescription = null,
                )
            }
        }
    }

    LaunchedEffect(initQuery) {
        query = initQuery
    }
}

@Preview
@Composable
private fun PreviewMapSearchNowView() {
    MapSearchView(
        initQuery = "테스트중",
        onSearch = { logMessage("ff") }
    )
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
    }
}