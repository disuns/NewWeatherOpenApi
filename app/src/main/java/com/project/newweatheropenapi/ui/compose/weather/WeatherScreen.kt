package com.project.newweatheropenapi.ui.compose.weather

import android.os.Build
import androidx.annotation.RequiresApi
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.bumptech.glide.integration.compose.GlideImage
import com.project.newweatheropenapi.utils.ComposeHelpManager
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ui.theme.Color_bbdefb
import com.project.newweatheropenapi.viewmodel.ActivityViewModel
import com.project.newweatheropenapi.viewmodel.WeatherViewModel

@RequiresApi(Build.VERSION_CODES.O)
@Composable
fun WeatherScreen(
    onNavigate : ()->Unit = {},
    activityViewModel: ActivityViewModel,
    viewModel: WeatherViewModel = hiltViewModel()) {

    val backgroundColor = Color_bbdefb
    val paddingStart = ComposeHelpManager.previewDimenResource(R.dimen.PaddingStart, 14.0f )
    val paddingEnd = ComposeHelpManager.previewDimenResource(R.dimen.PaddingEnd, 10.0f )

    Column(modifier = Modifier
        .fillMaxSize()
        .background(backgroundColor)
        .padding(start = paddingStart.dp, end = paddingEnd.dp),
        verticalArrangement = Arrangement.spacedBy(8.dp)) {
        TitleColumn()
        NowWeatherColumn()
        TimeWeatherColumn()
        WeekForecastSection()
    }
}

@Preview
@Composable
fun TitleColumn(){
    val text = ComposeHelpManager.previewStringResource(R.string.nowWeather,"현재날씨")
    val fontSize = ComposeHelpManager.previewDimenResource(R.dimen.WeatherTitle, 28.0f ).sp

    Box(modifier = Modifier
        .fillMaxWidth()
        .padding(vertical = 8.dp)){
        Text(
            text = text,
            style = TextStyle(
                fontSize = fontSize,
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}

@Preview
@Composable
fun NowWeatherColumn(){
    Column(modifier = Modifier
        .fillMaxSize()) {
        Row(modifier = Modifier
            .fillMaxWidth()
            .weight(4f)
            .padding(vertical = 8.dp)){
            NowWeatherImageColumn()
            NowWeatherTextColumn()
        }
        WeatherDetailsColumn()
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Preview
@Composable
private fun NowWeatherImageColumn(){
    val loadingImageText = ComposeHelpManager.previewStringResource(R.string.loadingImage,"이미지 로딩중")
    val imageSize = ComposeHelpManager.previewDimenResource(R.dimen.NowFcstIV, 110.0f ).dp

    Column(modifier = Modifier
        .fillMaxHeight(),
//        .weight(2f),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center) {
        GlideImage(model = R.drawable.rain,
            contentDescription = loadingImageText,
            modifier = Modifier
            .size(imageSize))
    }
}

@Preview
@Composable
fun NowWeatherTextColumn() {
    val nowTempSize = ComposeHelpManager.previewDimenResource(R.dimen.NowTempText,64.0f).sp
    val nowWeatherSize = ComposeHelpManager.previewDimenResource(R.dimen.NowWeatherText,30.0f).sp

    Column(
        modifier = Modifier
//            .weight(3f)
            .fillMaxHeight(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "123",
            fontSize = nowTempSize,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
        Text(
            text = "123",
            fontSize = nowWeatherSize,
            modifier = Modifier.align(Alignment.CenterHorizontally)
        )
    }
}

@Composable
fun WeatherDetailsColumn() {
    val nowWetRainWindSize = ComposeHelpManager.previewDimenResource(R.dimen.NowWetRainWindColumn,18.0f).sp

    Column(
        modifier = Modifier
            .fillMaxWidth(),
//            .weight(3f),
        horizontalAlignment = Alignment.Start,
        verticalArrangement = Arrangement.Center
    ) {
        Text(
            text = "현재 습도",
            fontSize = nowWetRainWindSize,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Text(
            text = "강수량",
            fontSize = nowWetRainWindSize,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
        Text(
            text = "바람",
            fontSize = nowWetRainWindSize,
            modifier = Modifier
                .weight(1f)
                .fillMaxWidth()
        )
    }
}

@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun TimeWeatherColumn() {
    val timeWeatherText = ComposeHelpManager.previewStringResource(R.string.timeWeather,"시간대별 예보")
    val loadingImageText = ComposeHelpManager.previewStringResource(R.string.loadingImage,"이미지 로딩중")
    val timeWeatherTextSize = ComposeHelpManager.previewDimenResource(R.dimen.WeatherRvTitle, 24.0f ).sp

    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .weight(4f)
    ) {
        GlideImage(model = R.drawable.dotted_line_long,
            contentDescription = loadingImageText,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp))

        Text(
            text = timeWeatherText,
            fontSize = timeWeatherTextSize,
            fontWeight = FontWeight.Bold,
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
                .align(Alignment.CenterHorizontally)
        )

//        ViewPager2(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp) // Adjust height as needed
//        )
    }
}

@Composable
fun WeekForecastSection() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
//            .weight(3f)
    ) {
//        Image(
//            painter = painterResource(id = R.drawable.dotted_line_long),
//            contentDescription = stringResource(id = R.string.imageLoadFail),
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//        )

//        Text(
//            text = stringResource(id = R.string.weekFcst),
//            fontSize = dimensionResource(id = R.dimen.WeatherRvTitle).toSp(),
//            fontWeight = FontWeight.Bold,
//            modifier = Modifier
//                .fillMaxWidth()
//                .padding(vertical = 8.dp)
//                .align(Alignment.CenterHorizontally)
//        )

//        RecyclerView(
//            modifier = Modifier
//                .fillMaxWidth()
//                .height(200.dp) // Adjust height as needed
//        )
    }
}

@Preview
@Composable
private fun Preview(){
    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
//        WeatherScreen(activityViewModel = ActivityViewModel())
    }
}