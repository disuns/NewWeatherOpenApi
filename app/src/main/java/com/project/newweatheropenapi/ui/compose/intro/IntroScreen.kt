package com.project.newweatheropenapi.ui.compose.intro

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.rememberMultiplePermissionsState
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ui.theme.Color_c3cdd3
import com.project.newweatheropenapi.utils.isNetworkCheck
import com.project.newweatheropenapi.utils.logMessage
import com.project.newweatheropenapi.utils.toastMessage


@Composable
fun IntroScreen(onNavigate: () -> Unit = {}) {
    PermissionCheck(onNavigate)

    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(color = Color_c3cdd3),
        contentAlignment = Alignment.Center
    ) {
        Image(
            modifier = Modifier.fillMaxSize(),
            painter = painterResource(R.drawable.intro),
            contentDescription = "null"
        )
    }
}

@OptIn(ExperimentalPermissionsApi::class)
@Composable
private fun PermissionCheck(onNavigate: () -> Unit = {}) {
    val context = LocalContext.current

    val multiplePermissionsState = rememberMultiplePermissionsState(
        permissions = listOf(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION
        )
    )

    LaunchedEffect(multiplePermissionsState) {
        when {
            multiplePermissionsState.allPermissionsGranted -> {
                if (context.isNetworkCheck()) {
                    onNavigate()
                } else {
                    logMessage(context.getString(R.string.noInternet))
                    toastMessage(context.getString(R.string.noInternet),context)
                }
            }

            multiplePermissionsState.shouldShowRationale -> {
                toastMessage(context.getString(R.string.gpsNeed), context)
                logMessage(context.getString(R.string.gpsNeed))
                openAppSettings(context)
            }

            else -> {
                multiplePermissionsState.launchMultiplePermissionRequest()
            }
        }
    }
}

private fun openAppSettings(context: Context) {
    val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
        data = Uri.fromParts("package", context.packageName, null)
    }
    context.startActivity(intent)
}

@Preview
@Composable
private fun Preview() {
    IntroScreen()
}