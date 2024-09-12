package com.project.newweatheropenapi.ui.compose

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.utils.managers.ComposeHelpManager

@Composable
fun TopLocationButton(modifier: Modifier, query : String = "", onClick:() -> Unit) {
    val padding = ComposeHelpManager.previewDimenResource(R.dimen.SearchButtonPadding, 10.0f).dp

    Button(
        onClick = onClick,
        modifier = modifier.fillMaxWidth(),
        colors = ButtonDefaults.buttonColors(containerColor = Color.White),
        shape = RectangleShape
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxSize()
        ) {
            Icon(
                painter = painterResource(id = R.drawable.ic_baseline_search_24),
                contentDescription = null,
                tint = Color.Black,
            )
            Spacer(modifier = Modifier.width(padding))
            Text(
                color = Color.Black,
                text = query,
                fontSize = dimensionResource(id = R.dimen.MainText).value.sp,
                modifier = Modifier.weight(1f),
                overflow = TextOverflow.Ellipsis,
                maxLines = 1
            )
        }
    }
}

@Preview
@Composable
fun PreviewTopLocationButton(){
    TopLocationButton(modifier = Modifier.fillMaxWidth().height(100.dp)){}
}