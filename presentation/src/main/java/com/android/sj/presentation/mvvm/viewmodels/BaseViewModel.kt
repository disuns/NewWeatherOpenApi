package com.android.sj.presentation.mvvm.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.sj.common.utils.logMessage
import com.android.sj.domain.ApiResult
import com.android.sj.presentation.event.UiEvent
import com.android.sj.presentation.models.state.uistate.BaseUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS>(
    initial: VS
) : ViewModel()  {
    private val _viewState = MutableStateFlow(initial)
    val viewState: StateFlow<VS> = _viewState.asStateFlow()

    private val _eventChannel = Channel<UiEvent>(Channel.BUFFERED)
    val events: Flow<UiEvent> = _eventChannel.receiveAsFlow()

    protected fun sendEvent(event: UiEvent) {
        viewModelScope.launch {
            _eventChannel.send(event)
        }
    }

    private fun updateViewState(data: VS.() -> VS) {
        _viewState.update { it.data() }
    }

    protected fun <T, UI> fetchData(
        usecase : Flow<ApiResult<T>>,
        mapper : (T) -> UI,
        onFetchStartBefore : () -> Unit = {},
        onFetchStartAfter : () -> Unit = {},
        onFetchSuccessBefore : (T) -> Unit = {},
        onFetchSuccessAfter : (T) -> Unit = {},
        onFetchErrorBefore : (String, Int) -> Unit = {_, _ ->},
        onFetchErrorAfter : (String, Int) -> Unit = {_, _ ->},
        onFetchEmptyBefore : () -> Unit = {},
        onFetchEmptyAfter : () -> Unit = {},
        updateState : VS.(BaseUiState<UI>) -> VS
    ) {
        viewModelScope.launch {
            usecase.onStart {
                onFetchStartBefore()
                updateViewState { updateState(BaseUiState(isLoading = true, isError = false)) }
                onFetchStartAfter()
            }.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        onFetchSuccessBefore(result.value)
                        val uiState = BaseUiState(
                            model = mapper(result.value),
                            isEmptyData = false,
                            isLoading = false,
                            isError = false
                        )
                        updateViewState { updateState(uiState) }
                        onFetchSuccessAfter(result.value)
                    }
                    is ApiResult.Error ->{
                        val errorMessage = result.exception?.message ?: "Unknown Error"
                        val resultCode = result.code ?: -1

                        onFetchErrorBefore(errorMessage, resultCode)
                        val uiState = BaseUiState<UI>(
                            isEmptyData = true,
                            isLoading = false,
                            isError = true,
                            errorCode = result.code,
                            errorMessage = result.exception?.message ?: "Unknown Error"
                        )
                        sendEvent(UiEvent.ShowToast(errorMessage))
                        updateViewState { updateState(uiState) }
                        onFetchErrorAfter(errorMessage, resultCode)
                    }
                    is ApiResult.Empty -> {
                        onFetchEmptyBefore()
                        val uiState = BaseUiState<UI>(
                            isLoading = false,
                            isError = false,
                            isEmptyData = true
                        )
                        updateViewState { updateState(uiState) }
                        onFetchEmptyAfter()
                    }
                }
            }
        }
    }

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