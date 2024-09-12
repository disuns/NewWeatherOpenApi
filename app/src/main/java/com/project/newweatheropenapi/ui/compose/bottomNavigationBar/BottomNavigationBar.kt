package com.project.newweatheropenapi.ui.compose.bottomNavigationBar

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalConfiguration
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.sealed.ScreenRoute
import com.project.newweatheropenapi.utils.logMessage

@Composable
fun BottomNavigationBar(navController: NavController) {
    val items = listOf(ScreenRoute.Weather, ScreenRoute.AirQuality)
    val itemTexts = stringArrayResource(R.array.Tab)

    val screenHeightDp = LocalConfiguration.current.screenHeightDp.dp
    val height = screenHeightDp / 15


    NavigationBar (modifier = Modifier.fillMaxWidth()
    .height(height)){
        val currentRoute = navController.currentBackStackEntry?.destination?.route
        items.forEachIndexed { index, screen ->
            NavigationBarItem(
                icon = {},
                label = { Text(itemTexts[index]) },
                selected = currentRoute == screen.route,
                onClick = {
                    navController.navigate(screen.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                            inclusive = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                }
            )
        }
    }
}