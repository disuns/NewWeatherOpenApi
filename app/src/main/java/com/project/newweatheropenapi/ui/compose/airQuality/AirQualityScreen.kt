package com.project.newweatheropenapi.ui.compose.airQuality

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.utils.managers.LocationDataManager
import com.project.newweatheropenapi.utils.sp
import com.project.newweatheropenapi.viewmodel.AirQualityViewModel

@Composable
fun AirQualityScreen(
    onNavigate: () -> Unit = {},
    viewModel: AirQualityViewModel = hiltViewModel(),
    locationDataManager: LocationDataManager
) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        Button(onClick = onNavigate) {
            
        }

        Text(
            text = stringResource(R.string.airQuality),
            fontSize = dimensionResource(R.dimen.Loading).sp(),
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun Preview(){
//    AirQualityScreen()
}