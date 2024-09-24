package com.project.newweatheropenapi.ui.compose.loading.skeleton

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.newweatheropenapi.utils.splashShimmerEffect

@Composable
fun SkeletonWeatherNow(modifier: Modifier){
    Column(modifier) {
        Row(
            modifier = Modifier
                .fillMaxWidth(0.7f)
                .weight(3.5f)
                .padding(top = 8.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .weight(1.5f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Spacer(
                    modifier = Modifier
                        .fillMaxHeight()
                        .aspectRatio(1.5f)
                        .splashShimmerEffect()
                )
            }
            Spacer(
                modifier = Modifier.fillMaxWidth(0.7f)
                    .height(124.5.dp)
                    .weight(1f).splashShimmerEffect()
            )
        }
        Spacer(
            modifier = Modifier
                    .fillMaxWidth(0.7f)
                .weight(2f).splashShimmerEffect()
        )
    }
}

@Preview
@Composable
fun PreviewSkeletonWeatherNow(){
    SkeletonWeatherNow(Modifier
        .fillMaxWidth().height(289.dp))
}