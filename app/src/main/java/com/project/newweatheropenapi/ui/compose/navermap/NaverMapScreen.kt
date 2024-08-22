package com.project.newweatheropenapi.ui.compose.navermap

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.project.newweatheropenapi.ComposeHelpManager
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.viewmodel.NaverMapViewModel

@Composable
fun NaverMapScreen(
    onNavigate : ()->Unit = {},
    viewModel: NaverMapViewModel = hiltViewModel()) {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        val text = ComposeHelpManager.previewStringResource(R.string.navermap,"navermap")
        val fontSize = ComposeHelpManager.previewDimenResource(R.dimen.Loading, 30.0f ).sp


        Button(onClick = onNavigate) {

        }
        Text(
            text = text,
            fontSize = fontSize,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview
@Composable
private fun Preview(){
    NaverMapScreen()
}