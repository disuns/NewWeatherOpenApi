package com.android.sj.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.ApiResult
import com.android.sj.presentation.models.state.BaseViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch

open class BaseViewModel<S: BaseViewState>(val initialState: S) : ViewModel()  {
    protected var currentState = initialState
    protected val _state : Channel<S> = Channel(Channel.BUFFERED)
    val state: Flow<S> = _state.receiveAsFlow().stateIn(viewModelScope, SharingStarted.Lazily, initialState)

    protected fun <T> fetchData(
        mapperAndUsecase: Channel<ApiResult<T>>,
        updateState: (currentState: S, result: ApiResult<T>) -> S
    ) {
        viewModelScope.launch {
            mapperAndUsecase.consumeEach{ result->
                currentState =updateState(currentState, result)
                _state.send(currentState)
            }
        }
    }

    open fun <T> handleIntent(intent : T) {}

    protected fun fetchAllData(
        vararg fetchActions: suspend () -> Unit
    ) {
        viewModelScope.launch {
            fetchActions.forEach { action ->
                action()
            }
        }
    }
}