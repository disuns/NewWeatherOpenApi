package com.android.sj.presentation.utils.managers

import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

internal object LoadingStateManager {
    private val _isLoading = MutableStateFlow(false)
    val isLoading : StateFlow<Boolean> = _isLoading.asStateFlow()

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
}