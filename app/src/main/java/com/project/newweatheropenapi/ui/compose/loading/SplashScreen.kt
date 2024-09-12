package com.project.newweatheropenapi.ui.compose.loading

import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.window.Dialog
import com.project.newweatheropenapi.utils.managers.LoadingStateManager

@Composable
fun SplashDialogScreen(){
    val isLoading = LoadingStateManager.isLoading.collectAsState().value

    if(isLoading) {
        Dialog(
            onDismissRequest = {LoadingStateManager.isShow(false)}
        ) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun SplashSkeletonScreen(){
}