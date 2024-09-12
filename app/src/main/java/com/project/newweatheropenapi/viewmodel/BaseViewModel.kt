package com.project.newweatheropenapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.newweatheropenapi.network.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel()  {
    protected fun <T> fetchData(
        apiCall: suspend () -> Flow<ApiResult<T>>,
        stateFlow: MutableStateFlow<ApiResult<T>>
    ) {
        viewModelScope.launch {
            apiCall().collect { result ->
                stateFlow.value = result
            }
        }

    }
}