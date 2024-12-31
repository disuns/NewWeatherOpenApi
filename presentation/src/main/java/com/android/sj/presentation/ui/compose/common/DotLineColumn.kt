package com.android.sj.presentation.ui.compose.common

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.android.sj.presentation.R
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun DotLineColumn(){
    GlideImage(
        model = R.drawable.dotted_line_long,
        contentDescription = stringResource(R.string.loadingImage),
        modifier = Modifier
            .fillMaxWidth()
            .padding(bottom = 8.dp)
    )
}