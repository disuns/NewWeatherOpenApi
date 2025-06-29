package com.android.sj.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.android.sj.domain.ApiResult
import com.android.sj.presentation.models.state.uistate.BaseUiState
import com.android.sj.presentation.models.state.viewstate.BaseViewState
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.channels.consumeEach
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

abstract class BaseViewModel<VS>(
    initial: VS
) : ViewModel()  {
    val _viewState = MutableStateFlow(initial)
    val viewState: StateFlow<VS> = _viewState.asStateFlow()

    open fun <T> handleIntent(intent : T) {}

    protected fun updateViewState(data: VS.() -> VS) {
        _viewState.update { it.data() }
    }

    protected fun <T, UI> fetchData(
        usecase : Flow<ApiResult<T>>,
        mapper : (T) -> UI,
        initialData : UI,
        updateState : VS.(BaseUiState<UI>) -> VS
    ) {
        viewModelScope.launch {
            usecase.onStart {
                updateViewState { updateState(BaseUiState(isLoading = true, isError = false)) }
            }.collect { result ->
                val uiState = when (result) {
                    is ApiResult.Success ->
                        BaseUiState(
                            model = mapper(result.value),
                            isLoading = false,
                            isError = false
                        )
                    is ApiResult.Error ->
                        BaseUiState(
                            isLoading = false,
                            isError = true,
                            errorMessage = result.exception?.message ?: "Unknown Error"
                        )
                    is ApiResult.Empty ->
                        BaseUiState(
                            model = initialData,
                            isLoading = false,
                            isError = false
                        )
                }
                updateViewState { updateState(uiState) }
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