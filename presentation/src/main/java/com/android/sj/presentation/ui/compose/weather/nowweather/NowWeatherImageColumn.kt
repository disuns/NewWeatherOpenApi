package com.android.sj.presentation.ui.compose.weather.nowweather

import android.graphics.drawable.Drawable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.res.stringResource
import com.android.sj.presentation.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun NowWeatherImageColumn(modifier: Modifier, weatherImg: Drawable?) {
    Column(
        modifier = modifier,
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        GlideImage(
            model = weatherImg,
            contentDescription = stringResource(R.string.loadingImage),
            modifier = Modifier
                .fillMaxHeight()
                .aspectRatio(1f),
            colorFilter = ColorFilter.tint(Color.Black)
        )
    }
}