package com.project.newweatheropenapi.ui.compose.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newweatheropenapi.ComposeHelpManager
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.viewmodel.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    onNavigate : ()->Unit = {},
    viewModel: WeatherViewModel = hiltViewModel()) {
//    {
//
//        Column(
//            modifier = Modifier
//                .fillMaxSize()
//                .padding(horizontal = 16.dp)
//        ) {
//            Column(
//                modifier = Modifier
//                    .weight(4f)
//                    .fillMaxWidth()
//            ) {
//                Row(
//                    modifier = Modifier
//                        .weight(4f)
//                        .fillMaxWidth()
//                        .align(Alignment.CenterHorizontally)
//                ) {
//                    Box(
//                        modifier = Modifier
//                            .weight(2f)
//                            .fillMaxHeight()
//                            .padding(8.dp),
//                        contentAlignment = Alignment.Center
//                    ) {
//                        Image(
//                            painter = nowFcstIV,
//                            contentDescription = stringResource(R.string.imageLoadFail),
//                            modifier = Modifier
//                                .size(64.dp)
//                        )
//                    }
//                    Column(
//                        modifier = Modifier
//                            .weight(3f)
//                            .fillMaxHeight()
//                            .padding(8.dp),
//                        horizontalAlignment = Alignment.CenterHorizontally
//                    ) {
//                        Text(
//                            text = nowTemp,
//                            style = TextStyle(
//                                fontSize = 18.sp
//                            )
//                        )
//                        Text(
//                            text = nowFcst,
//                            style = TextStyle(
//                                fontSize = 18.sp
//                            )
//                        )
//                    }
//                }
//
//                Row(
//                    modifier = Modifier
//                        .weight(3f)
//                        .fillMaxWidth()
//                        .padding(8.dp)
//                ) {
//                    Text(
//                        text = nowWet,
//                        style = TextStyle(
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier.weight(1f)
//                    )
//                    Text(
//                        text = nowRain,
//                        style = TextStyle(
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier.weight(1f)
//                    )
//                    Text(
//                        text = nowWind,
//                        style = TextStyle(
//                            fontSize = 16.sp
//                        ),
//                        modifier = Modifier.weight(1f)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Image(
//                painter = painterResource(id = R.drawable.dotted_line_long),
//                contentDescription = stringResource(R.string.imageLoadFail),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Text(
//                text = timeFcst,
//                style = TextStyle(
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold
//                ),
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//
//            LazyRow(
//                modifier = Modifier
//                    .weight(4f)
//                    .fillMaxWidth()
//            ) {
//                items(timeFcstItems) { item ->
//                    Text(
//                        text = item,
//                        modifier = Modifier
//                            .padding(horizontal = 4.dp)
//                            .align(Alignment.CenterVertically)
//                    )
//                }
//            }
//
//            Spacer(modifier = Modifier.height(8.dp))
//
//            Image(
//                painter = painterResource(id = R.drawable.dotted_line_long),
//                contentDescription = stringResource(R.string.imageLoadFail),
//                modifier = Modifier.fillMaxWidth()
//            )
//
//            Text(
//                text = weekFcst,
//                style = TextStyle(
//                    fontSize = 24.sp,
//                    fontWeight = FontWeight.Bold
//                ),
//                modifier = Modifier.align(Alignment.CenterHorizontally)
//            )
//
//            LazyColumn(
//                modifier = Modifier
//                    .weight(3f)
//                    .fillMaxWidth()
//            ) {
//                items(weekFcstItems) { item ->
//                    Text(
//                        text = item,
//                        modifier = Modifier
//                            .padding(vertical = 4.dp)
//                            .fillMaxWidth()
//                    )
//                }
//            }
//        }
//    }
}

@Preview
@Composable
fun Title(){
    val text = ComposeHelpManager.previewStringResource(R.string.nowWeather,"현재날씨")
    val fontSize = ComposeHelpManager.previewDimenResource(R.dimen.WeatherTitle, 28.0f ).sp

    Text(
        text = text,
        style = TextStyle(
            fontSize = fontSize,
            fontWeight = FontWeight.Bold
        )
    )
}

@Preview
@Composable
private fun Preview(){
//    WeatherScreen()
}