package com.project.newweatheropenapi.ui.compose.common

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.project.newweatheropenapi.network.ApiResult

@Composable
fun <T> ApiResultHandler(
    modifier: Modifier,
    state: ApiResult<T>,
    content: @Composable (ApiResult.Success<T>) -> Unit
) {
    when (state) {
        is ApiResult.Success -> {
            content(state)
        }
        is ApiResult.Empty -> DefaultEmpty(modifier)
        is ApiResult.Error -> DefaultError(modifier)
        is ApiResult.Loading -> DefaultLoading(modifier)
    }
}

@Composable
fun DefaultError(modifier: Modifier){
    Text(modifier = modifier, text = "에러")
}

@Composable
fun DefaultEmpty(modifier: Modifier){
    Text(modifier = modifier, text = "비었다")
}

@Composable
fun DefaultLoading(modifier: Modifier){
    Text(modifier = modifier, text = "로딩중")
}