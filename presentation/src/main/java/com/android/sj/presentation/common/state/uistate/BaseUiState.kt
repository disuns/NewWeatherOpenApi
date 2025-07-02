package com.android.sj.presentation.common.state.uistate

data class BaseUiState<T>(
    val model : T? = null,
    val isLoading : Boolean = false,
    val isError : Boolean = false,
    val errorMessage : String? = null,
    val errorCode : Int? = null,
    val isEmptyData : Boolean = false
)
