package com.project.newweatheropenapi.utils.Managers

import androidx.compose.runtime.Composable
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalInspectionMode
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.dimensionResource
import androidx.compose.ui.res.stringResource

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

    @Composable
    fun previewColorResource(resourceId : Int, hardcoding: Color) : Color {
        return if(LocalInspectionMode.current){
            hardcoding
        }else{
            colorResource(resourceId)
        }
    }

    @Composable
    fun previewImageResource(resourceId : Int, hardcoding: Color) : Color {
        return if(LocalInspectionMode.current){
            hardcoding
        }else{
            colorResource(resourceId)
        }
    }
}