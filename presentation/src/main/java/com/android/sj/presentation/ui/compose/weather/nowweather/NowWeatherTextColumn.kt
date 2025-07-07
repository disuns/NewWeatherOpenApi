package com.android.sj.presentation.ui.compose.weather.nowweather

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.dimensionResource
import com.android.sj.presentation.R
import com.android.sj.presentation.utils.sp

@Composable
fun NowWeatherTextColumn(modifier: Modifier, nowTemp: String, weatherText: String) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = nowTemp,
            fontSize = dimensionResource(R.dimen.NowTempText).sp(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = weatherText,
            fontSize = dimensionResource(R.dimen.NowWeatherText).sp(),
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}
