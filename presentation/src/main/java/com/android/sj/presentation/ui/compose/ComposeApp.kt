package com.android.sj.presentation.ui.compose

import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.android.sj.domain.managers.LocationDataManager
import com.android.sj.presentation.common.sealed.ScreenRoute
import com.android.sj.presentation.common.ui.compose.bottomNavigationBar.BottomNavigationBar
import com.android.sj.presentation.common.ui.compose.loading.DialogScreen
import com.android.sj.presentation.ui.ScreenNav
import com.android.sj.presentation.utils.managers.LoadingStateManager

@Composable
fun InitScreen(
    locationDataManager: LocationDataManager
) {
    val navController = rememberNavController()
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentRoute = navBackStackEntry?.destination?.route
    val isLoading = LoadingStateManager.isLoading.collectAsState().value
    val showBottoms = ScreenRoute.allRoutes.any { it.route == currentRoute && it.showBottom }

    if (isLoading && currentRoute != ScreenRoute.Intro.route) {
        DialogScreen()
    }
    Scaffold(
        bottomBar =  {
            if (showBottoms) BottomNavigationBar(navController)
        }
    ) { paddingValues ->
        ScreenNav(
            navController = navController,
            locationDataManager = locationDataManager,
            paddingValues = paddingValues
        )
    }
}