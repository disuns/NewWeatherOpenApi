package com.project.newweatheropenapi.ui.compose.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import com.project.newweatheropenapi.R
import com.project.newweatheropenapi.network.ApiResult
import com.project.newweatheropenapi.ui.theme.icon.EmptyImageVector
import com.project.newweatheropenapi.ui.theme.icon.ErrorImageVector

@Composable
fun <T> ApiResultHandler(
    modifier: Modifier,
    state: ApiResult<T>,
    errorFunc: ()->Unit,
    skeleton: @Composable () -> Unit  = {DefaultLoading(modifier)},
    content: @Composable (ApiResult.Success<T>) -> Unit
) {
    when (state) {
        is ApiResult.Success -> {
            content(state)
        }
        is ApiResult.Empty -> DefaultEmpty(modifier)
        is ApiResult.Error -> DefaultError(modifier){errorFunc()}
        is ApiResult.Loading -> DefaultLoading(modifier)
    }
}

@Composable
fun DefaultError(modifier: Modifier, function: () -> Unit){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize().clickable { function() }) {
        Icon(
            imageVector = ErrorImageVector,
            contentDescription = null,
            tint = Color.Black
        )
        Text(text = stringResource(R.string.error))
    }
}

@Composable
fun DataPotalSuccesError(modifier: Modifier, errorCodeToString : String){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()) {
        Icon(
            imageVector = ErrorImageVector,
            contentDescription = null,
            tint = Color.Black
        )
        Text(text = stringResource(R.string.error))
        Text(text = errorCodeToString)
    }

}

@Composable
fun DefaultEmpty(modifier: Modifier){
    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()) {
        Icon(
            imageVector = EmptyImageVector,
            contentDescription = null,
            tint = Color.Black
        )
        Text(text = stringResource(R.string.empty))
    }
}

@Composable
fun DefaultLoading(modifier: Modifier){
    Text(modifier = modifier, text = "로딩중")
}