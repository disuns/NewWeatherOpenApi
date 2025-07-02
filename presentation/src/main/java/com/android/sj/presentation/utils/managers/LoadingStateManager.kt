package com.android.sj.presentation.utils.managers

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

internal object LoadingStateManager {
    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading.asStateFlow()

    private var isPendingToShow = false
    private var pendingJob: Job? = null
    private const val MIN_LOADING_TIME_MS = 100

    private val coroutineScope = CoroutineScope(Dispatchers.Main)

    private var showCount = 0
    private val lock = Any()

    fun show() {
        synchronized(lock) {
            showCount += 1
            if (showCount == 1) {
                _isLoading.value = true
            }
        }
    }

    fun hide() {
        synchronized(lock) {
            if (showCount > 0) {
                showCount -= 1
                if (showCount == 0) {
                    _isLoading.value = false
                }
            }
        }
    }

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
}