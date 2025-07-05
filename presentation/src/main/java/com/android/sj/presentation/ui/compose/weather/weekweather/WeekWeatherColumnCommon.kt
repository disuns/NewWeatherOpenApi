package com.android.sj.presentation.ui.compose.weather.weekweather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.android.sj.presentation.R
import com.android.sj.presentation.ui.compose.common.UiStateHandler
import com.android.sj.presentation.utils.LocalLocationDataManager
import com.android.sj.presentation.utils.LocalWeatherVM
import com.android.sj.presentation.utils.sp

@Composable
fun WeekWeatherColumnCommon(
    modifier: Modifier,
    errorFunc: (String) -> Unit
) {
    val weatherState by LocalWeatherVM.current.viewState.collectAsStateWithLifecycle()

    val locationDataManager = LocalLocationDataManager.current

    val locationData = locationDataManager.locationData.collectAsStateWithLifecycle()
    val locationValue = locationData.value
    val address = locationValue.address

    UiStateHandler(modifier, weatherState.weekRainSkyUiState, errorFunc = {errorFunc(address)}) { successState ->
        Column(
            modifier = modifier
        ) {
            Text(
                text = stringResource(R.string.weekWeather),
                fontSize = dimensionResource(R.dimen.WeatherViewTitle).sp(),
                fontWeight = FontWeight.Bold,
                modifier = Modifier
                    .fillMaxWidth()
                    .align(Alignment.CenterHorizontally)
            )

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(vertical = 8.dp),
                verticalArrangement = Arrangement.spacedBy(3.dp)
            ) {
                items(successState.items.size) { item ->
                    if(successState.items[item].skyAm != null && successState.items[item].skyPm != null)
                        WeekWeatherItem(successState.items[item])
                }
            }
        }
    }
}

