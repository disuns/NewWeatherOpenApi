package com.project.newweatheropenapi.ui.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.material3.lightColorScheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.utils.sp

private val LightColorScheme = lightColorScheme(
    primary = Purple40,
    secondary = PurpleGrey40,
    tertiary = Pink40

    /* Other default colors to override
    background = Color(0xFFFFFBFE),
    surface = Color(0xFFFFFBFE),
    onPrimary = Color.White,
    onSecondary = Color.White,
    onTertiary = Color.White,
    onBackground = Color(0xFF1C1B1F),
    onSurface = Color(0xFF1C1B1F),
    */
)

@Composable
fun NewWeatherOpenApiTheme(
    content: @Composable () -> Unit
) {

    MaterialTheme(
        colorScheme = LightColorScheme,
        typography = Typography(bodyMedium = defaultTextStyle()),
        content = content
    )
}

@Composable
fun defaultTitleTextStyle() = TextStyle(
    fontSize = dimensionResource(R.dimen.DefaultTitle).sp(),
    fontWeight = FontWeight.Bold
)

@Composable
fun defaultTextStyle() = TextStyle(
    color = Color.Black,
    fontWeight = FontWeight.Normal
)
