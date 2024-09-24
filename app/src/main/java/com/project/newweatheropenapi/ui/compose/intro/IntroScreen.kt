package com.project.newweatheropenapi.ui.compose.intro

import android.Manifest
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.provider.Settings
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
import com.bumptech.glide.integration.compose.ExperimentalGlideComposeApi
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.ui.theme.Color_c3cdd3
import com.project.newweatheropenapi.utils.isNetworkCheck
import com.project.newweatheropenapi.utils.logMessage
import com.project.newweatheropenapi.utils.managers.LoadingStateManager
import com.project.newweatheropenapi.utils.toastMessage


@OptIn(ExperimentalGlideComposeApi::class)
@Composable
fun IntroScreen(onNavigate: () -> Unit = {}) {
    val context = LocalContext.current
    LaunchedEffect(Unit) {
        if(context.isNetworkCheck()){
            permissionCheck(onNavigate, context)
        }else{
            logMessage("인터넷 안됨")
        }
    }

    Box(
        modifier = Modifier.fillMaxSize()
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

private fun permissionCheck(onNavigate: () -> Unit = {}, context: Context) {
    TedPermission.create()
        .setPermissionListener(object : PermissionListener {
            override fun onPermissionGranted() {
                LoadingStateManager.isShow(isShow = true, isLoadingTimeCheck = false)
                onNavigate()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                toastMessage(context.getString(R.string.gpsNeed), context)
                logMessage(context.resources.getString(R.string.gpsNeed))
                if (!deniedPermissions.isNullOrEmpty()) {
                    openAppSettings(context)
                }
            }
        })
        .setRationaleMessage(context.resources.getString(R.string.gpsNeed))
        .setPermissions(
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.ACCESS_COARSE_LOCATION,
        )
        .check()
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