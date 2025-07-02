package com.android.sj.presentation.ui.compose.common

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.android.sj.presentation.R
import com.android.sj.presentation.common.state.uistate.BaseUiState
import com.android.sj.presentation.common.ui.theme.icon.EmptyImageVector
import com.android.sj.presentation.common.ui.theme.icon.ErrorImageVector
import com.android.sj.presentation.utils.dataPotalResultCode

@Composable
fun <T > UiStateHandler(
    modifier: Modifier,
    state: BaseUiState<T>,
    errorFunc: ()->Unit,
    skeleton: @Composable () -> Unit  = {DefaultLoading(modifier)},
    content: @Composable (T) -> Unit
) {
    when {
        state.isLoading -> DefaultLoading(modifier)
        state.isError -> {
            if(state.errorMessage == "PotalError"){
                DataPotalSuccesError(modifier, state.errorCode)
            }else{
                DefaultError(modifier){errorFunc()}
            }
        }
        state.isEmptyData->DefaultEmpty(modifier)
        state.model!=null-> content(state.model)
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
fun DataPotalSuccesError(modifier: Modifier, errorCode: Int?){
    val context = LocalContext.current
    val errorMessage = errorCode?.dataPotalResultCode(context)

    Column(verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier.fillMaxSize()) {
        Icon(
            imageVector = ErrorImageVector,
            contentDescription = null,
            tint = Color.Black
        )
        Text(text = stringResource(R.string.error))
        errorMessage?.let {
            Text(text = it)
        }
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
    Box(modifier= modifier){}
}