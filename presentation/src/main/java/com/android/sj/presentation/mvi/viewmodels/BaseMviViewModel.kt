package com.android.sj.presentation.mvi.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.ApiResult
import com.android.sj.presentation.common.event.UiEvent
import com.android.sj.presentation.common.state.uistate.BaseUiState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseMviViewModel<INTENT, VS>(
    initial: VS
) : ViewModel() {
    private val intentChannel = Channel<INTENT>(Channel.UNLIMITED)
    private val _viewState = MutableStateFlow(initial)
    val viewState: StateFlow<VS> = _viewState.asStateFlow()

    private val _effectChannel = Channel<UiEvent>(Channel.BUFFERED)
    val effects: Flow<UiEvent> = _effectChannel.receiveAsFlow()

    init {
        processIntents()
    }

    fun sendIntent(intent: INTENT) = intentChannel.trySend(intent)

    private fun processIntents() {
        viewModelScope.launch {
            for (intent in intentChannel) {
                handleIntent(intent)
            }
        }
    }

    protected abstract suspend fun handleIntent(intent: INTENT)

    protected fun updateState(newState: VS) {
        _viewState.value = newState
    }

    protected fun sendEffect(event: UiEvent) = viewModelScope.launch {
        _effectChannel.send(event)
    }

    protected fun <T, UI> fetchData(
        usecase: Flow<ApiResult<T>>,
        mapper: (T) -> UI,
        onFetchStartBefore: () -> Unit = {},
        onFetchStartAfter: () -> Unit = {},
        onFetchSuccessBefore: (T) -> Unit = {},
        onFetchSuccessAfter: (T) -> Unit = {},
        onFetchErrorBefore: (String, Int?) -> Unit = { _, _ -> },
        onFetchErrorAfter: (String, Int?) -> Unit = { _, _ -> },
        onFetchEmptyBefore: () -> Unit = {},
        onFetchEmptyAfter: () -> Unit = {},
        updateState: VS.(BaseUiState<UI>) -> VS
    ) {
        viewModelScope.launch {
            usecase.onStart {
                onFetchStartBefore()
                // 기존 상태에서 로딩 플래그만 올린 채로 partialState 적용
                updateState(_viewState.value.updateState(BaseUiState(isLoading = true, isError = false)))
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
                        updateState(_viewState.value.updateState(uiState))
                        onFetchSuccessAfter(result.value)
                    }
                    is ApiResult.Error -> {
                        val msg = result.exception?.message ?: "Unknown Error"
                        onFetchErrorBefore(msg, result.code)
                        sendEffect(UiEvent.ShowToast(msg))
                        val uiState = BaseUiState<UI>(
                            isEmptyData = true,
                            isLoading = false,
                            isError = true,
                            errorCode = result.code,
                            errorMessage = msg
                        )
                        updateState(_viewState.value.updateState(uiState))
                        onFetchErrorAfter(msg, result.code)
                    }
                    is ApiResult.Empty -> {
                        onFetchEmptyBefore()
                        val uiState = BaseUiState<UI>(
                            isLoading = false,
                            isError = false,
                            isEmptyData = true
                        )
                        updateState(_viewState.value.updateState(uiState))
                        onFetchEmptyAfter()
                    }
                }
            }
        }
    }
}