package com.project.newweatheropenapi.ui.compose.bottomNavigationBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.sealed.ScreenRoute
import com.project.newweatheropenapi.ui.theme.Color_747483
import com.project.newweatheropenapi.utils.logMessage

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(ScreenRoute.Weather, ScreenRoute.AirQuality)
    val itemTexts = stringArrayResource(R.array.Tab)

    val height = LocalConfiguration.current.screenHeightDp.dp / 15

    NavigationBar(
        modifier = Modifier
            .fillMaxWidth()
            .height(height),
        containerColor = Color.White
    ) {
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {},
                label = { Text(text = itemTexts[index],
                    fontSize = 17.sp) },
                selected = currentRoute == screen.route,
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = Color.Transparent,
                    selectedTextColor = Color.Black,
                    unselectedTextColor = Color_747483,
                ),
                onClick = {
                    if (currentRoute != screen.route){
                        navController.navigate(screen.route) {
                            popUpTo(screen.route) {
                                inclusive = true
                                saveState = true
                            }
                            launchSingleTop = true
                        }
                    }
                },
            )
        }
    }
}