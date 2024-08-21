package com.project.newweatheropenapi

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.project.newweatheropenapi.ui.compose.InitScreen
import com.project.newweatheropenapi.ui.theme.NewWeatherOpenApiTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            NewWeatherOpenApiTheme{
                InitScreen()
            }
        }
    }
}

private fun gpsCheckPermissionLocation() {
    permissionCheck()
}

private fun permissionCheck() {
//    TedPermission.create()
//        .setPermissionListener(permissionListener)
//        .setRationaleMessage(resources.getString(R.string.gpsNeed))
//        .setPermissions(Manifest.permission.ACCESS_FINE_LOCATION, Manifest.permission.ACCESS_COARSE_LOCATION)
//        .check()
}

//private val permissionListener = object : PermissionListener {
//    override fun onPermissionGranted() {
//        activityMainStart()
//    }
//
//    override fun onPermissionDenied(deniedPermissions: MutableList<String>?) {
//        weatherApplication.toastMessage(resources.getString(R.string.gpsNeed))
//        finish()
//    }
//}
//
//private fun activityMainStart() {
//    with(Intent(applicationContext, MainActivity::class.java)) {
//        putExtra(resources.getString(R.string.provider), provider)
//        startActivity(this)
//    }
//    finish()
//}