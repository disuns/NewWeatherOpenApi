package com.android.sj.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.ApiResult
import com.android.sj.presentation.event.UiEvent
import com.android.sj.presentation.utils.managers.LoadingStateManager
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.onCompletion
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<INTENT, VS, PS>(
    initial: VS
) : ViewModel() {
    private val intentChannel = Channel<INTENT>(Channel.UNLIMITED)
    private val partialStateChannel = Channel<PS>(Channel.UNLIMITED)

    private val _viewState = MutableStateFlow(initial)
    val viewState: StateFlow<VS> = _viewState.asStateFlow()

    private val _effects = Channel<UiEvent>(Channel.BUFFERED)
    val effects: Flow<UiEvent> = _effects.receiveAsFlow()

    init {
        viewModelScope.launch {
            processIntents()
        }
        viewModelScope.launch {
            processPartialStates()
        }
    }

    fun sendIntent(intent: INTENT) = intentChannel.trySend(intent)
    private fun sendPartial(partial: PS) = partialStateChannel.trySend(partial)
    protected fun sendEffect(event: UiEvent) = viewModelScope.launch {
        _effects.send(event)
    }

    private suspend fun processIntents() {
        for (intent in intentChannel) {
            handleIntent(intent)
        }
    }

    private suspend fun processPartialStates(){
        for (partial in partialStateChannel) {
            _viewState.update { current ->
                reduceState(current, partial)
            }
        }
    }
    protected abstract fun reduceState(current: VS, partial: PS): VS

    protected abstract suspend fun handleIntent(intent: INTENT)

    protected fun <T, UI> fetchAndReduce(
        usecase: Flow<ApiResult<T>>,
        mapper: (T) -> UI,
        onFetchStartBefore: () -> Unit = {},
        onFetchStartAfter: () -> Unit = {},
        onFetchSuccessBefore: (T) -> Unit = {},
        onFetchSuccessAfter: (UI) -> Unit = {},
        onFetchErrorBefore: (String, Int?) -> Unit = { _, _ -> },
        onFetchErrorAfter: (String, Int?) -> Unit = { _, _ -> },
        onFetchEmptyBefore: () -> Unit = {},
        onFetchEmptyAfter: () -> Unit = {},
        emitLoading: PS,
        emitEmpty: PS,
        emitError: (String, Int?) -> PS,
        emitSuccess: (UI) -> PS
    ) {
        viewModelScope.launch {
            usecase.onStart {
                onFetchStartBefore()
                LoadingStateManager.show()
                sendPartial(emitLoading)
                onFetchStartAfter()
            }.onCompletion {
                LoadingStateManager.hide()
            }.collect { result ->
                when (result) {
                    is ApiResult.Success -> {
                        onFetchSuccessBefore(result.value)
                        val uiModel = mapper(result.value)
                        sendPartial(emitSuccess(uiModel))
                        onFetchSuccessAfter(uiModel)
                    }
                    is ApiResult.Error -> {
                        val msg = result.exception?.message ?: "Unknown Error"
                        onFetchErrorBefore(msg, result.code)
                        sendEffect(UiEvent.ShowToast(msg))
                        sendPartial(emitError(msg, result.code))
                        onFetchErrorAfter(msg, result.code)
                    }
                    is ApiResult.Empty -> {
                        onFetchEmptyBefore()
                        sendPartial(emitEmpty)
                        onFetchEmptyAfter()
                    }
                }
            }
        }
    }
}