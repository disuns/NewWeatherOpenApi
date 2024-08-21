package com.project.newweatheropenapi

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource
import javax.inject.Inject

object ComposeHelpManager{

    @Composable
    fun previewStringResource(resourceId : Int, hardcoding: String) : String{
        return if(LocalInspectionMode.current){
            hardcoding
        }else{
            stringResource(resourceId)
        }
    }

    @Composable
    fun previewDimenResource(resourceId : Int, hardcoding: Float) : Float {
        return if(LocalInspectionMode.current){
            hardcoding
        }else{
            dimensionResource(resourceId).value
        }
    }
}