package com.project.newweatheropenapi.ui.compose

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ui.theme.Default_BackGround
import com.project.newweatheropenapi.ui.theme.icon.SearchImageVector
import com.project.newweatheropenapi.utils.sp

@Composable
fun ScreenWithTopLocationButton(
    onClick: () -> Unit,
    address: String,
    content: @Composable (Modifier) -> Unit
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
    ) {
        TopLocationButton(
            onClick = onClick,
            modifier = Modifier
                .fillMaxWidth(),
            query = address
        )
        content(
            Modifier.fillMaxSize()
        )
    }
}

@Composable
fun TopLocationButton(modifier: Modifier, query: String = "", onClick: () -> Unit) {
    Box(
        modifier = modifier
            .fillMaxWidth()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(Color.Black, Default_BackGround),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            )
            .padding(horizontal = 16.dp, vertical = 12.5.dp),
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .align(Alignment.Center)
                .clickable { onClick() }
                .background(
                    color = Color(0x4DFFFFFF),
                    shape = RoundedCornerShape(999.dp)
                ),
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
            Text(
                color = Color.White,
                text = query,
                fontSize = dimensionResource(id = R.dimen.MainText).sp(),
                modifier = Modifier.fillMaxWidth().weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun PreviewTopLocationButton() {
    TopLocationButton(modifier = Modifier
        .fillMaxWidth()
        .height(100.dp)) {}
}