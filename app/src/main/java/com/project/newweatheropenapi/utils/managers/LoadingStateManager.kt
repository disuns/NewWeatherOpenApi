package com.project.newweatheropenapi.utils.managers

import com.project.newweatheropenapi.network.ApiResult
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

object LoadingStateManager {
    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading.asStateFlow()

    private var isPendingToShow = false
    private var pendingJob: Job? = null
    private const val MIN_LOADING_TIME_MS = 100

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    fun isShow(isShow: Boolean, isLoadingTimeCheck : Boolean = true) {
        if (isShow) {
            if (isLoadingTimeCheck) {
                startLoadingDelay()
            } else {
                _isLoading.value = true
            }
        } else {
            stopLoading()
        }
    }

    private fun startLoadingDelay() {
        isPendingToShow = true
        pendingJob?.cancel()

        pendingJob = coroutineScope.launch {
            delay(MIN_LOADING_TIME_MS.toLong())
            if (isPendingToShow) {
                _isLoading.value = true
            }
        }
    }

    private fun stopLoading() {
        isPendingToShow = false
        pendingJob?.cancel()

        _isLoading.value = false
    }


    fun isAnyLoadingCheck(vararg stateFlow: ApiResult<*>){
        isShow(stateFlow.any{it is ApiResult.Loading})
    }
}