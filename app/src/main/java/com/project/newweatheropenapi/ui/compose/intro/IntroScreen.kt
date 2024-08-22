package com.project.newweatheropenapi.ui.compose.intro

import android.Manifest
import android.content.Context
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import com.gun0912.tedpermission.PermissionListener
import com.gun0912.tedpermission.normal.TedPermission
import com.project.newweatheropenapi.ComposeHelpManager
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.app.WeatherApplication


@Composable
fun IntroScreen(onNavigate : ()->Unit = {}) {
    val context = LocalContext.current
    permissionCheck(onNavigate, context)

    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
//        (context.applicationContext as WeatherApplication).isNetworkCheck()
        val text = ComposeHelpManager.previewStringResource(R.string.loading,"Loading")
        val fontSize = ComposeHelpManager.previewDimenResource(R.dimen.Loading, 30.0f ).sp

        Text(
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

private fun permissionCheck(onNavigate: () -> Unit = {}, context: Context) {
    TedPermission.create()
        .setPermissionListener(object : PermissionListener {
            override fun onPermissionGranted() {
                onNavigate()
            }

            override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
                WeatherApplication().toastMessage(stringResource(id = )context.resources.getString(R.string.gpsNeed))
                WeatherApplication().debug(context.resources.getString(R.string.gpsNeed))
            }
        })
        .setRationaleMessage(context.resources.getString(R.string.gpsNeed))
        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
        .check()
}

@Preview
@Composable
private fun Preview(){
    IntroScreen()
}