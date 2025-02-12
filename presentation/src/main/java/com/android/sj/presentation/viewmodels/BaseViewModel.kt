package com.android.sj.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.ApiResult
import com.android.sj.presentation.models.state.BaseViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.scan
import kotlinx.coroutines.launch

open class BaseViewModel<S: BaseViewState>(val initialState: S) : ViewModel()  {
    protected var currentState = initialState
    protected val _state : Channel<S> = Channel(Channel.BUFFERED)
    val state: Flow<S> = _state.receiveAsFlow()

    protected fun <T> fetchData(
        mapperAndUsecase: Channel<ApiResult<T>>,
        updateState: (currentState: S, result: ApiResult<T>) -> S
    ) {
        viewModelScope.launch {
            mapperAndUsecase
                .receiveAsFlow()
                .scan(initialState) { previousState, result ->
                    updateState(previousState, result)
                }
                .collect { newState ->
                    _state.send(newState)
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

    protected fun updateChannelState(update : S.() -> S){
        currentState  = currentState.update()
        _state.trySend(currentState)
    }
}