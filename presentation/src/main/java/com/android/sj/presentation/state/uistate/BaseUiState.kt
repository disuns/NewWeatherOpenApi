package com.android.sj.presentation.state.uistate

data class BaseUiState<T>(
    val model : T? = null,
    val isLoading : Boolean = false, //로딩 스켈레톤용
    val isError : Boolean = false,
    val errorMessage : String? = null,
    val errorCode : Int? = null,
    val isEmptyData : Boolean = false
)
