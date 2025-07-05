package com.android.sj.presentation.ui.compose.navermap

import androidx.compose.runtime.Composable
import com.android.sj.presentation.intent.NaverMapIntent
import com.android.sj.presentation.utils.LocalNaverMapVM

@Composable
fun NaverMapScreen() {
    val viewModel = LocalNaverMapVM.current

    NaverMapScreenCommon { lon, lat ->
        viewModel.sendIntent(NaverMapIntent.LoadNaverMapGeo(lon, lat))
    }
}






