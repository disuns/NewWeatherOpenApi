package com.project.newweatheropenapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.newweatheropenapi.dataclass.state.BaseViewState
import com.project.newweatheropenapi.network.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch

open class BaseViewModel<S: BaseViewState>(initialState: S) : ViewModel()  {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state

    protected fun <T> fetchData(
        apiCall: suspend () -> Flow<ApiResult<T>>,
        updateState: (S, ApiResult<T>) -> S
    ) {
        viewModelScope.launch {
            apiCall().collect { result ->
                _state.value = updateState(_state.value, result)
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