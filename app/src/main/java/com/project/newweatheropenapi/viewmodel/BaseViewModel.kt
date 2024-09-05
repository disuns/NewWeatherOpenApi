package com.project.newweatheropenapi.viewmodel

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.project.newweatheropenapi.network.ApiResult
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch

open class BaseViewModel : ViewModel()  {
    private var isFetch = false

    protected fun <T> fetchData(
        apiCall: suspend () -> Flow<ApiResult<T>>,
        stateFlow: MutableStateFlow<ApiResult<T>>
    ) {
        if(isFetch) return
        isFetch = true

        viewModelScope.launch {
            apiCall().collect { result ->
                stateFlow.value = result
                if(result is ApiResult.Success || result is ApiResult.Error)
                    isFetch = false
            }
        }

    }
}